package com.llj.lib.utils;

import android.app.Activity;

import java.util.LinkedList;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/4/26
 */
public class AActivityStackUtils {
    private static final LinkedList<Activity> sActivityList = new LinkedList<>();

    public static int size() {
        return sActivityList.size();
    }

    /**
     * 获得当前最顶层的activity
     *
     * @return 当前最顶层的activity
     */
    public static Activity getCurrentActivity() {
        if (sActivityList.size() >= 1) {
            return sActivityList.get(sActivityList.size() - 1);
        }
        return null;
    }

    /**
     * 生成Activity存入列表
     *
     * @param activity
     */
    public static void addCurrentActivity(Activity activity) {
        if (activity != null)
            sActivityList.add(activity);
    }

    /**
     * 移除当前的activity
     *
     * @param activity
     */
    public static void removeCurrentActivity(Activity activity) {
        if (activity != null)
            sActivityList.remove(activity);
    }

    /**
     * 获得顶层下面的activity
     *
     * @return
     */
    public static Activity getPreviousActivity() {
        if (sActivityList.size() >= 2) {
            return sActivityList.get(sActivityList.size() - 2);
        }
        return null;
    }

    /**
     * 清除最上层以下所有的activity
     */
    public static void clearBottomActivities() {
        if (sActivityList.size() >= 1) {
            Activity lastActivity = sActivityList.get(sActivityList.size() - 1);
            for (int i = 0; i < sActivityList.size() - 1; i++) {
                Activity activity = sActivityList.get(i);
                if (activity != null)
                    activity.finish();
            }
            sActivityList.clear();
            sActivityList.add(lastActivity);
        }
    }

    /**
     * 清除所有的activity
     */
    public static void removeAllActivity() {
        for (int i = 0; i < sActivityList.size(); i++) {
            Activity activity = sActivityList.get(i);
            if (activity != null)
                activity.finish();
        }
        sActivityList.clear();
    }

    public static void exitApp() {
        removeAllActivity();
        AToastUtils.destroyToast();
        System.exit(0);
    }

}
