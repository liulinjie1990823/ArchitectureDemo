package com.llj.socialization.login.model;

import com.llj.socialization.ThirdCommonResult;
import com.llj.socialization.login.LoginPlatformType;
import com.llj.socialization.share.SharePlatformType;

public class LoginResult extends ThirdCommonResult implements LoginPlatformType {
    public static final String RESPONSE_LOGIN_SUCCESS    = "LOGIN_SUCCESS";//分享成功
    public static final String RESPONSE_LOGIN_FAILURE    = "LOGIN_FAILURE";//分享失败
    public static final String RESPONSE_LOGIN_HAS_CANCEL = "LOGIN_HAS_CANCEL";//分享已取消
    public static final String RESPONSE_SHARE_NOT_INSTALL = "RESPONSE_LOGIN_NOT_INSTALL";//应用没安装

    private @SharePlatformType.Platform int    mPlatform;//分享的类型
    private                             String mResponse;//分享成功或者失败或者其他
    private                             String mMessage;//分享返回的信息

    private Object    mObject;
    private BaseToken mToken;
    private BaseUser  mUserInfo;

    public LoginResult(int platform, String response, String message) {
        mPlatform = platform;
        mResponse = response;
        this.mMessage = message;
    }

    public LoginResult(int platform, String response) {
        mPlatform = platform;
        mResponse = response;
    }

    public LoginResult(int platform, BaseToken token) {
        mPlatform = platform;
        mToken = token;
    }

    public LoginResult(int platform, String response, BaseToken token) {
        mPlatform = platform;
        mResponse = response;
        mToken = token;
    }

    public LoginResult(int platform, BaseToken token, BaseUser userInfo) {
        mPlatform = platform;
        mToken = token;
        mUserInfo = userInfo;
    }

    public LoginResult(int platform, Object object, BaseToken token, BaseUser userInfo) {
        mObject = object;
        mToken = token;
        mUserInfo = userInfo;
        mPlatform = platform;
    }

    public LoginResult(int platform, String response, Object object, BaseToken token, BaseUser userInfo) {
        mPlatform = platform;
        mResponse = response;
        mObject = object;
        mToken = token;
        mUserInfo = userInfo;
    }

    public int getPlatform() {
        return mPlatform;
    }

    public void setPlatform(int platform) {
        this.mPlatform = platform;
    }

    public BaseToken getToken() {
        return mToken;
    }

    public void setToken(BaseToken token) {
        mToken = token;
    }

    public BaseUser getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(BaseUser userInfo) {
        mUserInfo = userInfo;
    }
}
