package com.llj.lib.base;

import android.app.Activity;

import com.llj.lib.utils.AActivityStackUtils;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public interface IActivityStack {

    default Activity getCurrentActivity() {
        return AActivityStackUtils.getCurrentActivity();
    }

    /**
     * 生成Activity存入列表
     *
     * @param activity
     */
    default void addCurrentActivity(Activity activity) {
        AActivityStackUtils.addCurrentActivity(activity);
    }

    /**
     * 移除当前的activity
     *
     * @param activity
     */
    default void removeCurrentActivity(Activity activity) {
        AActivityStackUtils.removeCurrentActivity(activity);
    }

    /**
     * 获得顶层下面的activity
     *
     * @return
     */
    default Activity getPreviousActivity() {
        return AActivityStackUtils.getPreviousActivity();
    }

    /**
     * 清除最上层以下所有的activity
     */
    default void clearBottomActivities() {
        AActivityStackUtils.clearBottomActivities();
    }


    /**
     * 清除所有的activity
     */
    default void removeAllActivity() {
        AActivityStackUtils.removeAllActivity();
    }

    default void exitApp() {
        AActivityStackUtils.exitApp();
    }

}
