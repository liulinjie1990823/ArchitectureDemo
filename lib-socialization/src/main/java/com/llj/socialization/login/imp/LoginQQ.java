package com.llj.socialization.login.imp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.llj.socialization.INFO;
import com.llj.socialization.InstallUtil;
import com.llj.socialization.Logger;
import com.llj.socialization.login.LoginPlatformType;
import com.llj.socialization.login.callback.LoginListener;
import com.llj.socialization.login.interfaces.LoginInterface;
import com.llj.socialization.login.model.BaseToken;
import com.llj.socialization.login.model.LoginResult;
import com.llj.socialization.login.model.QQToken;
import com.llj.socialization.login.model.QQUser;
import com.llj.socialization.share.SocialManager;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/16.
 */


public class LoginQQ implements LoginInterface {
    private static final String SCOPE = "all";

    private Tencent     mTencent;
    private IUiListener mIUiListener;

    private LoginListener mLoginListener;

    public LoginQQ(final Context context, final LoginListener listener, final boolean fetchUserInfo) {
        mTencent = Tencent.createInstance(SocialManager.CONFIG.getQqId(), context.getApplicationContext());
        mLoginListener = listener;
        mIUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Logger.e(INFO.QQ_AUTH_SUCCESS);
                try {
                    //
                    QQToken token = QQToken.parse((JSONObject) o);
                    //
                    mTencent.setAccessToken(token.getAccessToken(), token.getExpires_in() + "");
                    mTencent.setOpenId(token.getOpenid());
                    //
                    if (fetchUserInfo) {
                        mLoginListener.beforeFetchUserInfo(token);
                        fetchUserInfo(context, token);
                    } else {
                        mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.QQ,LoginResult.RESPONSE_LOGIN_SUCCESS, token));
                    }
                } catch (JSONException e) {
                    Logger.e(INFO.ILLEGAL_TOKEN);
                    mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_FAILURE, e.getMessage()));
                }
            }

            @Override
            public void onError(UiError uiError) {
                Logger.e(INFO.QQ_LOGIN_ERROR);
                mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_FAILURE, uiError.errorDetail));
            }

            @Override
            public void onCancel() {
                Logger.e(INFO.AUTH_CANCEL);
                mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_HAS_CANCEL));
            }
        };
    }

    @Override
    public void doLogin(final Activity activity, final boolean fetchUserInfo) {
        mTencent.login(activity, SCOPE, mIUiListener);
    }

    @Override
    public void fetchUserInfo(Context context, final BaseToken token) {
        UserInfo info = new UserInfo(context, mTencent.getQQToken());
        info.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                Logger.e("QQLogin:" + object.toString());
                QQUser qqUser;
                try {
                    qqUser = QQUser.parse(token.getOpenid(), (JSONObject) object);
                    mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.QQ,LoginResult.RESPONSE_LOGIN_SUCCESS, object, token, qqUser));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_FAILURE, uiError.errorDetail));
            }

            @Override
            public void onCancel() {
                mLoginListener.onLoginResponse(new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_HAS_CANCEL));
            }
        });
    }

    @Override
    public void handleResult(int requestCode, int resultCode, Intent data) {
        Tencent.handleResultData(data, mIUiListener);
    }

    @Override
    public boolean isInstalled(Context context) {
        return InstallUtil.isQQInstalled(context);
    }

    @Override
    public void recycle() {
        mTencent.releaseResource();
        mIUiListener = null;
        mLoginListener = null;
        mTencent = null;
    }
}
