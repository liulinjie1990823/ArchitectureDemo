package com.llj.login.interceptor;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.AppManager;

/**
 * WeddingBazaar.
 * <p>
 * 对通过ARouter进行内页跳转的处理，Bundle中带有needLogin=1会先跳转到登录页
 * <p>
 * author llj
 * date 2019/1/11
 */

@Interceptor(priority = 1)
public class LoginInterceptor implements IInterceptor {
    public static final String TAG = LoginInterceptor.class.getSimpleName();

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Bundle bundle = postcard.getExtras();
        int needLogin = bundle.getInt(CRouter.AROUTER_NEED_LOGIN);
        if (needLogin == 1 && !isLogin()) {
            //没有登录
            if (callback != null) {
                callback.onInterrupt(null);
            }
            //需要把目标页设置到bundle中
            bundle.putString(CRouter.AROUTER_FORWARD_PATH, postcard.getPath());
            ARouter.getInstance().build(AppManager.getInstance().getJumpConfig().getLoginPath())
                    .with(bundle)
                    .withInt(CRouter.AROUTER_NEED_LOGIN, 0)
                    .navigation();
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        Log.i(TAG, "LoginInterceptor 初始化");
    }

    private static boolean isLogin() {
        return AppManager.getInstance().getUserInfoConfig() != null && AppManager.getInstance().getUserInfoConfig().isLogin();
    }


}
