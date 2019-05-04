package com.llj.lib.utils;

import android.app.Activity;

import java.util.LinkedList;

/**
 * ArchitectureDemo
 * describe:
 *
 * @author liulj
 * @date 2018/4/26
 */
public class AActivityStackUtils {
    private static final LinkedList<Activity> S_ACTIVITY_LIST = new LinkedList<>();

    public static int size() {
        return S_ACTIVITY_LIST.size();
    }

    /**
     * 获得当前最顶层的activity
     *
     * @return 当前最顶层的activity
     */
    public static Activity getCurrentActivity() {
        if (S_ACTIVITY_LIST.size() >= 1) {
            return S_ACTIVITY_LIST.get(S_ACTIVITY_LIST.size() - 1);
        }
        return null;
    }

    /**
     * 生成Activity存入列表
     *
     * @param activity
     */
    public static void addCurrentActivity(Activity activity) {
        if (activity != null) {
            S_ACTIVITY_LIST.add(activity);
        }
    }

    /**
     * 移除当前的activity
     *
     * @param activity
     */
    public static void removeCurrentActivity(Activity activity) {
        if (activity != null) {
            S_ACTIVITY_LIST.remove(activity);
        }
    }

    /**
     * 获得顶层下面的activity
     *
     * @return
     */
    public static Activity getPreviousActivity() {
        if (S_ACTIVITY_LIST.size() >= 2) {
            return S_ACTIVITY_LIST.get(S_ACTIVITY_LIST.size() - 2);
        }
        return null;
    }

    /**
     * 清除最上层以下所有的activity
     */
    public static void clearBottomActivities() {
        if (S_ACTIVITY_LIST.size() >= 1) {
            Activity lastActivity = S_ACTIVITY_LIST.get(S_ACTIVITY_LIST.size() - 1);
            for (int i = 0; i < S_ACTIVITY_LIST.size() - 1; i++) {
                Activity activity = S_ACTIVITY_LIST.get(i);
                if (activity != null) {
                    activity.finish();
                }
            }
            S_ACTIVITY_LIST.clear();
            S_ACTIVITY_LIST.add(lastActivity);
        }
    }

    /**
     * 清除所有的activity
     */
    public static void removeAllActivity() {
        for (int i = 0; i < S_ACTIVITY_LIST.size(); i++) {
            Activity activity = S_ACTIVITY_LIST.get(i);
            if (activity != null) {
                activity.finish();
            }
        }
        S_ACTIVITY_LIST.clear();
    }

    public static void exitApp() {
        removeAllActivity();
        AToastUtils.destroyToast();
        System.exit(0);
    }

}
