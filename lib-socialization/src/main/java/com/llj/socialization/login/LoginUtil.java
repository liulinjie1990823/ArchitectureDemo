package com.llj.socialization.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.llj.socialization.ResponseActivity;
import com.llj.socialization.log.INFO;
import com.llj.socialization.log.Logger;
import com.llj.socialization.login.callback.LoginListener;
import com.llj.socialization.login.interfaces.ILogin;
import com.llj.socialization.login.model.BaseToken;
import com.llj.socialization.login.model.LoginResult;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public class LoginUtil {
    public static final String TAG  = LoginUtil.class.getSimpleName();
    public static final int    TYPE = 799;

    private static ILogin        mLoginInstance;
    private static LoginListener mLoginListenerWrap;

    private static int mPlatform;

    private static boolean sFetchUserInfo;
    private static boolean sFetchWxToken;


    public static void login(Context context, @LoginPlatformType.Platform int platform, LoginListener listener) {
        login(context, platform, listener, true);
    }

    public static void login(Context context, @LoginPlatformType.Platform int platform, LoginListener listener, boolean fetchUserInfo) {
        login(context, platform, listener, fetchUserInfo, true);
    }

    public static void login(Context context, @LoginPlatformType.Platform int platform, LoginListener listener, boolean fetchUserInfo, boolean fetchWxToken) {
        listener.setPlatform(platform);

        mPlatform = platform;
        mLoginListenerWrap = buildWrapListener(listener);
        sFetchUserInfo = fetchUserInfo;
        sFetchWxToken = fetchWxToken;

        ResponseActivity.start(context, TYPE, platform);
    }

    private static LoginListenerWrap buildWrapListener(LoginListener listener) {
        return new LoginListenerWrap(listener);
    }

    public static void perform(Activity activity) {
        mLoginInstance = getPlatform(mPlatform, activity);

        if (mLoginListenerWrap == null || mLoginInstance == null) {
            activity.finish();
            return;
        }
        if (!mLoginInstance.isInstalled(activity)) {
            mLoginListenerWrap.onLoginResponse(new LoginResult(mPlatform, LoginResult.RESPONSE_SHARE_NOT_INSTALL));
            activity.finish();
            return;
        }
        mLoginListenerWrap.onStart();

        mLoginInstance.doLogin(activity);
    }

    private static ILogin getPlatform(@LoginPlatformType.Platform int platform, Activity activity) {
        Class clazz;
        ILogin login = null;
        try {
            switch (platform) {
                case LoginPlatformType.QQ:
                    clazz = Class.forName("com.llj.lib.socialization.qq.login.LoginQQ");
                    break;
                case LoginPlatformType.SINA:
                    clazz = Class.forName("com.llj.lib.socialization.sina.login.LoginSina");
                    break;
                case LoginPlatformType.WECHAT:
                    clazz = Class.forName("com.llj.lib.socialization.wechat.login.LoginWeChat");
                    break;
                default:
                    clazz = Class.forName("com.llj.lib.socialization.qq.login.LoginQQ");
                    break;
            }
            login = (ILogin) clazz.newInstance();
            login.init(activity.getApplicationContext(), mLoginListenerWrap, sFetchUserInfo, sFetchWxToken);

        } catch (Exception e) {

        }
        return login;
    }

    public static void handleResult(int requestCode, int resultCode, Intent data) {
        if (mLoginInstance != null) {
            mLoginInstance.handleResult(requestCode, resultCode, data);
        }
    }

    public static void recycle() {
        if (mLoginInstance != null) {
            mLoginInstance.recycle();
        }
        mLoginInstance = null;
        mLoginListenerWrap = null;
        mPlatform = 0;
        sFetchUserInfo = false;
        sFetchWxToken = false;
    }

    private static class LoginListenerWrap extends LoginListener {

        private LoginListener mListener;

        LoginListenerWrap(LoginListener listener) {
            mListener = listener;
            setPlatform(mListener.getPlatform());
        }

        @Override
        public void onStart() {
            mListener.onStart();
        }

        @Override
        public void beforeFetchUserInfo(BaseToken token) {
            Logger.e(INFO.LOGIN_AUTH_SUCCESS);
            mListener.beforeFetchUserInfo(token);
        }

        @Override
        public void onLoginResponse(LoginResult result) {
            mListener.onLoginResponse(result);
        }
    }
}
