package com.llj.socialization.login.model;

import androidx.annotation.StringDef;

import com.llj.socialization.ThirdCommonResult;
import com.llj.socialization.login.LoginPlatformType;
import com.llj.socialization.share.SharePlatformType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LoginResult extends ThirdCommonResult implements LoginPlatformType {
    public static final String RESPONSE_LOGIN_SUCCESS     = "RESPONSE_LOGIN_SUCCESS";//登录成功
    public static final String RESPONSE_LOGIN_FAILURE     = "RESPONSE_LOGIN_FAILURE";//登录失败
    public static final String RESPONSE_LOGIN_HAS_CANCEL  = "RESPONSE_LOGIN_HAS_CANCEL";//登录已取消
    public static final String RESPONSE_LOGIN_AUTH_DENIED = "RESPONSE_LOGIN_AUTH_DENIED";//被拒绝
    public static final String RESPONSE_SHARE_NOT_INSTALL = "RESPONSE_LOGIN_NOT_INSTALL";//登录没安装


    @StringDef({RESPONSE_LOGIN_SUCCESS, RESPONSE_LOGIN_FAILURE, RESPONSE_LOGIN_HAS_CANCEL,RESPONSE_LOGIN_AUTH_DENIED, RESPONSE_SHARE_NOT_INSTALL})
    @Retention(RetentionPolicy.SOURCE)
    @interface Response {
    }

    private @SharePlatformType.Platform int mPlatform;//登录的类型

    private String    mResponse;//登录成功或者失败或者其他
    private String    mMessage;//登录返回的信息
    private Object    mObject;//登录返回未解析的对象
    private BaseToken mToken;
    private BaseUser  mUserInfo;


    public LoginResult(int platform, @LoginResult.Response String response) {
        mPlatform = platform;
        mResponse = response;
    }

    public LoginResult(int platform, @LoginResult.Response String response, String message) {
        mPlatform = platform;
        mResponse = response;
        mMessage = message;
    }


    public LoginResult(int platform, @LoginResult.Response String response, BaseToken token) {
        mPlatform = platform;
        mResponse = response;
        mToken = token;
    }

    //成功
    public LoginResult(int platform, @LoginResult.Response String response, BaseToken token, BaseUser userInfo) {
        mPlatform = platform;
        mResponse = response;
        mToken = token;
        mUserInfo = userInfo;
    }

    public LoginResult(int platform, @LoginResult.Response String response, Object object, BaseToken token, BaseUser userInfo) {
        mPlatform = platform;
        mResponse = response;
        mObject = object;
        mToken = token;
        mUserInfo = userInfo;
    }

    public int getPlatform() {
        return mPlatform;
    }

    public String getResponse() {
        return mResponse;
    }

    public String getMessage() {
        return mMessage;
    }

    public Object getObject() {
        return mObject;
    }

    public BaseToken getToken() {
        return mToken;
    }

    public BaseUser getUserInfo() {
        return mUserInfo;
    }
}
