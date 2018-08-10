package com.llj.socialization.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.llj.socialization.ResponseActivity;
import com.llj.socialization.login.LoginPlatformType;
import com.llj.socialization.pay.callback.PayListener;
import com.llj.socialization.pay.imp.PayWechat;
import com.llj.socialization.pay.interfaces.PayInterface;
import com.llj.socialization.pay.model.PayParam;
import com.llj.socialization.pay.model.PayResult;

/**
 * project:android
 * describe:
 * Created by llj on 2017/8/13.
 */

public class PayUtil {
    public static final String TAG  = PayUtil.class.getSimpleName();
    public static final int    TYPE = 780;

    private static PayInterface mPayInterface;
    private static PayListener  mPayListenerWrap;

    private static int      mPlatform;
    private static PayParam mPayParam;


    public static void pay(Context context, @LoginPlatformType.Platform int platform, PayParam payParam, PayListener listener) {
        listener.setPlatform(platform);

        mPlatform = platform;
        mPayParam = payParam;
        mPayListenerWrap = buildWrapListener(listener);

        ResponseActivity.start(context, TYPE, platform);
    }

    private static PayListenerWrap buildWrapListener(PayListener listener) {
        return new PayListenerWrap(listener);
    }


    public static void perform(Activity activity) {
        mPayInterface = getPlatform(mPlatform, activity);

        if (mPayListenerWrap == null || mPayInterface == null) {
            activity.finish();
            return;
        }
        if (!mPayInterface.isInstalled(activity)) {
            mPayListenerWrap.onPayResponse(new PayResult(mPlatform,PayResult.RESPONSE_PAY_NOT_INSTALL));
            activity.finish();
            return;
        }
        mPayListenerWrap.onStart();
        mPayInterface.doPay(mPayParam);
    }

    private static PayInterface getPlatform(@PayPlatformType.Platform int platform, Context context) {
        switch (platform) {
            case PayPlatformType.WECHAT:
                return new PayWechat(context, mPayListenerWrap);
        }
        return null;
    }

    public static void handleResult(int requestCode, int resultCode, Intent data) {
        if (mPayInterface != null) {
            mPayInterface.handleResult(requestCode, resultCode, data);
        }
    }

    private static class PayListenerWrap extends PayListener {

        private PayListener mListener;

        PayListenerWrap(PayListener listener) {
            mListener = listener;
            setPlatform(mListener.getPlatform());
        }

        @Override
        public void onStart() {
            mListener.onStart();
        }

        @Override
        public void onPayResponse(PayResult result) {
            mListener.onPayResponse(result);
        }
    }

    public static void recycle() {
        if (mPayInterface != null) {
            mPayInterface.recycle();
        }
        mPayInterface = null;
        mPayListenerWrap = null;
        mPlatform = 0;
    }
}
