package com.llj.socialization.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.llj.socialization.ResponseActivity;
import com.llj.socialization.pay.callback.PayListener;
import com.llj.socialization.pay.interfaces.IPay;
import com.llj.socialization.pay.model.PayParam;
import com.llj.socialization.pay.model.PayResult;

/**
 * project:android describe: Created by llj on 2017/8/13.
 */

public class PayUtil {

  public static final String  TAG  = PayUtil.class.getSimpleName();
  public static final int     TYPE = 780;
  public static       boolean sIsInProcess;//避免华为手机多次调用WXEntryActivity，通过变量判断是否已经启动过ResponseActivity


  private static IPay        sMIPay;
  private static PayListener mPayListenerWrap;

  private static int      mPlatform;
  private static PayParam mPayParam;


  public static void pay(Context context, @PayPlatformType.Platform int platform, PayParam payParam,
      PayListener listener) {
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
    sMIPay = getPlatform(mPlatform, activity);

    if (mPayListenerWrap == null || sMIPay == null) {
      activity.finish();
      return;
    }
    if (!sMIPay.isInstalled(activity)) {
      mPayListenerWrap.onPayResponse(new PayResult(mPlatform, PayResult.RESPONSE_PAY_NOT_INSTALL));
      activity.finish();
      return;
    }
    mPayListenerWrap.onStart();
    sMIPay.doPay(mPayParam);
  }

  private static IPay getPlatform(@PayPlatformType.Platform int platform, Context context) {
    Class clazz;
    IPay pay = null;
    try {
      switch (platform) {
        case PayPlatformType.WECHAT:
          clazz = Class.forName("com.llj.lib.socialization.wechat.pay.PayWechat");
          break;
        default:
          clazz = Class.forName("com.llj.lib.socialization.wechat.pay.PayWechat");
          break;
      }
      pay = (IPay) clazz.newInstance();
      pay.init(context, mPayListenerWrap);
    } catch (Exception e) {
    }
    return pay;
  }


  public static void onNewIntent(Activity activity, Intent data) {
    if (sMIPay != null) {
      sMIPay.onNewIntent(data);
    }
  }

  public static void handleResult(Activity activity, int requestCode, int resultCode, Intent data) {
    if (sMIPay != null) {
      sMIPay.handleResult(requestCode, resultCode, data);
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
    if (sMIPay != null) {
      sMIPay.recycle();
    }
    sMIPay = null;
    mPayListenerWrap = null;
    mPlatform = 0;
  }
}
