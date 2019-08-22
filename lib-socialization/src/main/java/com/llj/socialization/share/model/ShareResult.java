package com.llj.socialization.share.model;

import android.support.annotation.StringDef;

import com.llj.socialization.ThirdCommonResult;
import com.llj.socialization.share.SharePlatformType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public class ShareResult extends ThirdCommonResult implements SharePlatformType {
    public static final String RESPONSE_SHARE_SUCCESS     = "RESPONSE_SHARE_SUCCESS";//分享成功
    public static final String RESPONSE_SHARE_FAILURE     = "RESPONSE_SHARE_FAILURE";//分享失败
    public static final String RESPONSE_SHARE_HAS_CANCEL  = "RESPONSE_SHARE_HAS_CANCEL";//分享已取消
    public static final String RESPONSE_SHARE_AUTH_DENIED = "RESPONSE_SHARE_AUTH_DENIED";//分享被拒绝
    public static final String RESPONSE_SHARE_NOT_INSTALL = "RESPONSE_SHARE_NOT_INSTALL";//应用没安装

    @StringDef({RESPONSE_SHARE_SUCCESS, RESPONSE_SHARE_FAILURE, RESPONSE_SHARE_HAS_CANCEL, RESPONSE_SHARE_AUTH_DENIED, RESPONSE_SHARE_NOT_INSTALL})
    @Retention(RetentionPolicy.SOURCE)
    @interface Response {
    }

    private @SharePlatformType.Platform int    mPlatform;//分享的类型
    private                             String mResponse;//分享成功或者失败或者其他
    private                             String mMessage;//分享返回的信息


    public ShareResult(@SharePlatformType.Platform int platform, String response) {
        mPlatform = platform;
        mResponse = response;
    }

    public ShareResult(@SharePlatformType.Platform int platform, String response, String message) {
        mPlatform = platform;
        mResponse = response;
        mMessage = message;
    }

    public int getPlatform() {
        return mPlatform;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        mResponse = response;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
