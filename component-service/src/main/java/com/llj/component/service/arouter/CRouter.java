package com.llj.component.service.arouter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/7/3
 */
public class CRouter {

    //app
    public static final String APP_MAIN_ACTIVITY              = "/app/MainActivity";
    public static final String APP_SECOND_ACTIVITY            = "/app/SecondActivity";
    public static final String APP_TOUCH_EVENT_ACTIVITY       = "/app/TouchEventActivity";
    public static final String APP_RECYCLE_VIEW_ACTIVITY      = "/app/RecycleViewActivity";
    public static final String APP_NESTED_SCROLLVIEW_ACTIVITY = "/app/NestedScrollViewActivity";
    public static final String APP_LINEAR_LAYOUT_ACTIVITY     = "/app/LinearLayoutActivity";
    public static final String APP_COMPONENT_ACTIVITY         = "/app/ComponentActivity";
    public static final String APP_SHARE_ACTIVITY             = "/app/ShareActivity";
    public static final String APP_MEMORY_LEAK_ACTIVITY       = "/app/MemoryLeakActivity";
    public static final String APP_APT_ACTIVITY      = "/app/AptActivity";
    public static final String APP_HOOK_ACTIVITY              = "/app/HookActivity";
    public static final String WIDGET_CONSTRAINT_ACTIVITY     = "/app/widget/ConstraintActivity";
    public static final String CIRCLE_VIEW_ACTIVITY           = "/app/widget/CircleViewActivity";

    //login
    public static final String LOGIN_LOGIN_ACTIVITY    = "/login/LoginActivity";
    public static final String LOGIN_REGISTER_ACTIVITY = "/login/RegisterActivity";

    public static void start(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public static void start(Context context, String path) {
        ARouter.getInstance().build(path).navigation(context);
    }

    public static void start(String path, Context context, int requestCode) {
        ARouter.getInstance().build(path).navigation((Activity) context, requestCode);
    }

    public static Object start(Context context, String path, String key, Object value) {
        return ARouter.getInstance().build(path)
                .withObject(key, value)
                .navigation(context);
    }

    public static Object start(Context context, String path, String key, String value) {
        return ARouter.getInstance().build(path)
                .withString(key, value)
                .navigation(context);
    }


    public static Object start(Context context, String path, String key, int value) {
        return ARouter.getInstance().build(path)
                .withInt(key, value)
                .navigation(context);
    }


    public static Object start(Context context, String path, String key, long value) {
        return ARouter.getInstance().build(path)
                .withLong(key, value)
                .navigation(context);
    }


    public static Object start(Context context, String path, String key, boolean value) {
        return ARouter.getInstance().build(path)
                .withBoolean(key, value)
                .navigation(context);
    }


    public static Object start(Context context, String path, Bundle bundle) {
        return ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(context);
    }


}
