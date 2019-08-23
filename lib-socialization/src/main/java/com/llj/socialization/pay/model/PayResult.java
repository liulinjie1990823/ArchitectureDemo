package com.llj.socialization.pay.model;

import android.support.annotation.StringDef;

import com.llj.socialization.pay.PayPlatformType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * project:android
 * describe:
 * Created by llj on 2017/8/13.
 */

public class PayResult {
    public static final String RESPONSE_PAY_SUCCESS     = "RESPONSE_PAY_SUCCESS";//成功
    public static final String RESPONSE_PAY_FAILURE     = "RESPONSE_PAY_FAILURE";//失败
    public static final String RESPONSE_PAY_HAS_CANCEL  = "RESPONSE_PAY_HAS_CANCEL";//已取消
    public static final String RESPONSE_PAY_AUTH_DENIED = "RESPONSE_PAY_AUTH_DENIED";//被拒绝
    public static final String RESPONSE_PAY_NOT_INSTALL = "RESPONSE_PAY_NOT_INSTALL";//没安装

    @StringDef({RESPONSE_PAY_SUCCESS, RESPONSE_PAY_FAILURE, RESPONSE_PAY_HAS_CANCEL,RESPONSE_PAY_AUTH_DENIED, RESPONSE_PAY_NOT_INSTALL})
    @Retention(RetentionPolicy.SOURCE)
    @interface Response {
    }

    private @PayPlatformType.Platform int    mPlatform;//分享的类型
    private                           String mResponse;//分享成功或者失败或者其他
    private                           String mMessage;//分享返回的信息

    public PayResult(int platform, String response) {
        mPlatform = platform;
        mResponse = response;
    }

    public PayResult(int platform, String response, String message) {
        mPlatform = platform;
        mResponse = response;
        this.mMessage = message;
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
}
