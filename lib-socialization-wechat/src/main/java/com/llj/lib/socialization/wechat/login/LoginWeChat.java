package com.llj.lib.socialization.wechat.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.llj.socialization.ResponseActivity;
import com.llj.socialization.init.SocialManager;
import com.llj.socialization.log.INFO;
import com.llj.socialization.log.Logger;
import com.llj.socialization.login.LoginPlatformType;
import com.llj.socialization.login.callback.LoginListener;
import com.llj.socialization.login.interfaces.ILogin;
import com.llj.socialization.login.model.BaseToken;
import com.llj.socialization.login.model.LoginResult;
import com.llj.socialization.login.model.WxToken;
import com.llj.socialization.login.model.WxUser;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/16.
 */

public class LoginWeChat implements ILogin {
    public static final String SCOPE_USER_INFO = "snsapi_userinfo";

    private static final String BASE_URL = "https://api.weixin.qq.com/sns/";

    private IWXAPI             mIWXAPI;
    private Context            mContext;
    private LoginListener      mLoginListener;
    private IWXAPIEventHandler mIWXAPIEventHandler;

    private OkHttpClient mClient;
    private boolean      mFetchUserInfo;
    private boolean      mFetchWxToken;

    @Override
    public void init(Context context, LoginListener listener, boolean fetchUserInfo, boolean fetchWxToken) {
        this.mContext = context;
        this.mLoginListener = listener;
        this.mIWXAPI = WXAPIFactory.createWXAPI(context.getApplicationContext(), SocialManager.getConfig(context.getApplicationContext()).getWxId());
        this.mClient = new OkHttpClient();
        this.mFetchUserInfo = fetchUserInfo;
        this.mFetchWxToken = fetchUserInfo;

        mIWXAPIEventHandler = new IWXAPIEventHandler() {
            @Override
            public void onReq(BaseReq baseReq) {
            }

            @Override
            public void onResp(BaseResp baseResp) {
                Log.e("LoginWeChat", "onResp:" + "resp.getType():" + baseResp.getType() + "resp.errCode:" + baseResp.errCode + "resp.errStr:" + baseResp.errStr);

                if (baseResp instanceof SendAuth.Resp && baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                    SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                    switch (resp.errCode) {
                        case BaseResp.ErrCode.ERR_OK:
                            if (mFetchWxToken) {
                                getToken(resp.code);
                            } else {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.putOpt("code", resp.code);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_SUCCESS, jsonObject.toString(), null, null));
                            }
                            break;
                        case BaseResp.ErrCode.ERR_USER_CANCEL:
                            Logger.e(INFO.WX_ERR_LOGIN_CANCEL);
                            mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_HAS_CANCEL, INFO.WX_ERR_LOGIN_CANCEL));
                            break;
                        case BaseResp.ErrCode.ERR_SENT_FAILED:
                            Logger.e(INFO.WX_ERR_SENT_FAILED);
                            mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_FAILURE, INFO.WX_ERR_SENT_FAILED));
                            break;
                        case BaseResp.ErrCode.ERR_UNSUPPORT:
                            Logger.e(INFO.WX_ERR_UNSUPPORT);
                            mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_FAILURE, INFO.WX_ERR_UNSUPPORT));
                            break;
                        case BaseResp.ErrCode.ERR_AUTH_DENIED:
                            Logger.e(INFO.WX_ERR_AUTH_DENIED);
                            mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_FAILURE, INFO.WX_ERR_AUTH_DENIED));
                            break;
                        default:
                            Logger.e(INFO.WX_ERR_AUTH_ERROR);
                            mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_FAILURE, INFO.WX_ERR_AUTH_ERROR));
                            break;
                    }
                }
            }
        };
    }

    @Override
    public void doLogin(Activity activity) {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = SCOPE_USER_INFO;
        req.state = String.valueOf(System.currentTimeMillis());
        mIWXAPI.sendReq(req);
    }

    /**
     * 获取WxToken
     *
     * @param code
     */
    private void getToken(final String code) {
        Task.callInBackground(new Callable<WxToken>() {
            @Override
            public WxToken call() throws Exception {
                Request request = new Request.Builder().url(buildTokenUrl(code)).build();
                Response response = mClient.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(response.body().string());
                WxToken token = WxToken.parse(jsonObject);
                return token;
            }
        }).continueWith(new Continuation<WxToken, Void>() {
            @Override
            public Void then(Task<WxToken> task) throws Exception {
                if (task.getError() != null) {
                    mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_FAILURE, task.getError().getMessage()));
                    return null;
                }
                Logger.e(INFO.WX_FETCH_TOKEN_SUCCESS);

                if (mFetchUserInfo) {
                    mLoginListener.beforeFetchUserInfo(task.getResult());
                    fetchUserInfo(null, task.getResult());
                } else {
                    mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_SUCCESS, task.getResult()));
                }
                return null;
            }
        });
    }


    @Override
    public void fetchUserInfo(Context context, final BaseToken token) {
        Task.callInBackground(new Callable<WxUser>() {
            @Override
            public WxUser call() throws Exception {
                Request request = new Request.Builder().url(buildUserInfoUrl(token)).build();
                Response response = mClient.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(response.body().string());
                WxUser user = WxUser.parse(jsonObject);
                return user;
            }
        }).continueWith(new Continuation<WxUser, Void>() {
            @Override
            public Void then(Task<WxUser> task) throws Exception {
                if (task.getError() != null) {
                    mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_FAILURE, task.getError().getMessage()));
                } else {
                    Logger.e(INFO.WX_FETCH_USER_INFO_SUCCESS);

                    mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_SUCCESS, token, task.getResult()));
                }
                return null;
            }
        });
    }

    @Override
    public void handleResult(int requestCode, int resultCode, Intent data) {
        if (ResponseActivity.class.getName().equals(data.getComponent().getClassName())) {
            mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.WECHAT, LoginResult.RESPONSE_LOGIN_HAS_CANCEL));
            return;
        }
        mIWXAPI.handleIntent(data, mIWXAPIEventHandler);
    }

    @Override
    public boolean isInstalled(Context context) {
        return mIWXAPI.isWXAppInstalled();
    }

    @Override
    public void recycle() {
        if (mIWXAPI != null) {
            mIWXAPI.detach();
        }
    }


    private String buildTokenUrl(String code) {
        return BASE_URL
                + "oauth2/access_token?appid="
                + SocialManager.getConfig(mContext).getWxId()
                + "&secret="
                + SocialManager.getConfig(mContext).getWxSecret()
                + "&code="
                + code
                + "&grant_type=authorization_code";
    }

    private String buildUserInfoUrl(BaseToken token) {
        return BASE_URL
                + "userinfo?access_token="
                + token.getAccessToken()
                + "&openid="
                + token.getOpenid();
    }
}
