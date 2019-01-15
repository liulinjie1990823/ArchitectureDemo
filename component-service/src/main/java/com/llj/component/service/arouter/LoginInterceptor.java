package com.llj.component.service.arouter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * WeddingBazaar.
 * describe:
 * author llj
 * date 2019/1/11
 */
public class LoginInterceptor implements IInterceptor {
    public static final String TAG = LoginInterceptor.class.getSimpleName();

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        Bundle bundle = postcard.getExtras();
        int needLogin = bundle.getInt(CRouter.AROUTER_NEED_LOGIN);
        if (needLogin == 0) {
            //不需要登录
            callback.onContinue(postcard);
        } else {
            //需要登录
            bundle.putString(CRouter.AROUTER_FORWARD_PATH, postcard.getPath());

            if (TextUtils.isEmpty(getAccessToken())) {
                callback.onInterrupt(null);
                //没有登录
                ARouter.getInstance().build(CRouter.LOGIN_PHONE_LOGIN)
                        .with(bundle)
                        .withInt(CRouter.AROUTER_NEED_LOGIN, 0)
                        .navigation();
            } else {
                //已经登录
                callback.onContinue(postcard);
            }
        }
    }

    private String getAccessToken() {
        return "";
    }

    @Override
    public void init(Context context) {
        Log.i(TAG, "LoginInterceptor 初始化");
    }
}
