package com.llj.lib.socialization.sina.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.llj.lib.socialization.sina.api.UsersAPI;
import com.llj.lib.socialization.sina.login.model.WeiboToken;
import com.llj.socialization.login.LoginPlatformType;
import com.llj.socialization.login.callback.LoginListener;
import com.llj.socialization.login.interfaces.ILogin;
import com.llj.socialization.login.model.BaseToken;
import com.llj.socialization.login.model.LoginResult;
import com.llj.socialization.share.SocialManager;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/16.
 */

public class LoginSina implements ILogin {


    private SsoHandler    mSsoHandler;
    private LoginListener mLoginListener;

    @Override
    public void init(Context context, LoginListener listener, boolean fetchUserInfo) {
        AuthInfo authInfo = new AuthInfo(context, SocialManager.getConfig(context).getSignId(),
                SocialManager.getConfig(context).getSignRedirectUrl(),
                SocialManager.getConfig(context).getSignScope());
        WbSdk.install(context, authInfo);

        mSsoHandler = new SsoHandler((Activity) context);

        mLoginListener = listener;
    }


    @Override
    public void doLogin(final Activity activity, final boolean fetchUserInfo) {
        mSsoHandler.authorizeClientSso(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                AccessTokenKeeper.writeAccessToken(activity, oauth2AccessToken);
                //
                WeiboToken weiboToken = WeiboToken.parse(oauth2AccessToken);
                weiboToken.setExpires_in(oauth2AccessToken.getExpiresTime());
                //
                if (fetchUserInfo) {
                    mLoginListener.beforeFetchUserInfo(weiboToken);
                    fetchUserInfo(activity, weiboToken, oauth2AccessToken);
                } else {
                    mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.SINA,LoginResult.RESPONSE_LOGIN_SUCCESS, weiboToken));
                }
            }

            @Override
            public void cancel() {
                mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.SINA, LoginResult.RESPONSE_LOGIN_HAS_CANCEL));
            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.SINA, LoginResult.RESPONSE_LOGIN_FAILURE, wbConnectErrorMessage.getErrorMessage()));
            }
        });
    }

    public void fetchUserInfo(Context context, final BaseToken token, Oauth2AccessToken accessToken) {
        UsersAPI mUsersAPI = new UsersAPI(context, SocialManager.getConfig(context).getSignId(), accessToken);
//        mUsersAPI.show(Long.parseLong(accessToken.getUid()), new RequestListener() {
//            @Override
//            public void onComplete(String response) {
//                Logger.e("sinaLogin:" + response);
//                if (!TextUtils.isEmpty(response)) {
////                    User user = User.parse(response);
////                    RequestUtil.loginThird(baseFragmentActivity, user, mAccessToken.getUid(), mAccessToken.getExpiresTime());
//                    WeiboUser user = null;
//                    try {
//                        user = WeiboUser.parse(new JSONObject(response));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.SINA,LoginResult.RESPONSE_LOGIN_SUCCESS, token, user));
//                }
//            }
//
//            @Override
//            public void onWeiboException(WeiboException e) {
//                mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.SINA, LoginResult.RESPONSE_LOGIN_FAILURE, e.getMessage()));
//            }
//        });
    }

    @Override
    public void fetchUserInfo(Context context, BaseToken token) {


    }

    @Override
    public void handleResult(int requestCode, int resultCode, Intent data) {
        mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
    }

    @Override
    public boolean isInstalled(Context context) {
        return  WbSdk.isWbInstall(context);
    }

    @Override
    public void recycle() {
        mSsoHandler = null;
        mLoginListener = null;
    }
}
