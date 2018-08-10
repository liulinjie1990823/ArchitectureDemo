package com.llj.socialization.login.imp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.llj.socialization.InstallUtil;
import com.llj.socialization.Logger;
import com.llj.socialization.login.LoginPlatformType;
import com.llj.socialization.login.callback.LoginListener;
import com.llj.socialization.login.interfaces.LoginInterface;
import com.llj.socialization.login.model.BaseToken;
import com.llj.socialization.login.model.LoginResult;
import com.llj.socialization.login.model.WeiboToken;
import com.llj.socialization.login.model.WeiboUser;
import com.llj.socialization.login.wbapi.AccessTokenKeeper;
import com.llj.socialization.login.wbapi.UsersAPI;
import com.llj.socialization.share.SocialManager;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/16.
 */

public class LoginSina implements LoginInterface {


    private SsoHandler    mSsoHandler;
    private LoginListener mLoginListener;

    public LoginSina(final Activity activity, final LoginListener listener, final boolean fetchUserInfo) {
        AuthInfo authInfo = new AuthInfo(activity, SocialManager.CONFIG.getSignId(),
                SocialManager.CONFIG.getSignRedirectUrl(),
                SocialManager.CONFIG.getSignScope());
        WbSdk.install(activity, authInfo);

        mSsoHandler = new SsoHandler(activity);

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
        UsersAPI mUsersAPI = new UsersAPI(context, SocialManager.CONFIG.getSignId(), accessToken);
        mUsersAPI.show(Long.parseLong(accessToken.getUid()), new RequestListener() {
            @Override
            public void onComplete(String response) {
                Logger.e("sinaLogin:" + response);
                if (!TextUtils.isEmpty(response)) {
//                    User user = User.parse(response);
//                    RequestUtil.loginThird(baseFragmentActivity, user, mAccessToken.getUid(), mAccessToken.getExpiresTime());
                    WeiboUser user = null;
                    try {
                        user = WeiboUser.parse(new JSONObject(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.SINA,LoginResult.RESPONSE_LOGIN_SUCCESS, token, user));
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.SINA, LoginResult.RESPONSE_LOGIN_FAILURE, e.getMessage()));
            }
        });
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
        return InstallUtil.isSinaInstalled(context);
    }

    @Override
    public void recycle() {
        mSsoHandler = null;
        mLoginListener = null;
    }
}
