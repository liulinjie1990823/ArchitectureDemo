package com.llj.socialization.pay.model;

import com.llj.socialization.pay.PayPlatformType;

/**
 * project:android
 * describe:
 * Created by llj on 2017/8/13.
 */

public class PayResult {
    public static final String RESPONSE_PAY_SUCCESS     = "RESPONSE_PAY_SUCCESS";//分享成功
    public static final String RESPONSE_PAY_FAILURE     = "RESPONSE_PAY_FAILURE";//分享失败
    public static final String RESPONSE_PAY_HAS_CANCEL  = "RESPONSE_PAY_HAS_CANCEL";//分享已取消
    public static final String RESPONSE_PAY_AUTH_DENIED = "RESPONSE_PAY_AUTH_DENIED";//分享被拒绝
    public static final String RESPONSE_PAY_NOT_INSTALL = "RESPONSE_PAY_NOT_INSTALL";//没安装

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
}
