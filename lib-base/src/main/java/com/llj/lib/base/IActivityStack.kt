package com.llj.lib.base

import android.app.Activity

import com.llj.lib.utils.AActivityStackUtils

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
interface IActivityStack {

    /**
     * 获得当前最顶层的activity
     *
     * @return 当前最顶层的activity
     */
    fun getCurrentActivity(activity: Activity): Activity? {
        return AActivityStackUtils.getCurrentActivity()
    }


    /**
     * 获得顶层下面的activity
     *
     * @return
     */
    fun getPreviousActivity(activity: Activity): Activity? {
        return AActivityStackUtils.getPreviousActivity()
    }


    /**
     * 生成Activity存入列表
     *
     * @param activity
     */
    fun addCurrentActivity(activity: Activity) {
        AActivityStackUtils.addCurrentActivity(activity)
    }

    /**
     * 移除当前的activity
     *
     * @param activity
     */
    fun removeCurrentActivity(activity: Activity) {
        AActivityStackUtils.removeCurrentActivity(activity)
    }

    /**
     * 清除最上层以下所有的activity
     */
    fun clearBottomActivities() {
        AActivityStackUtils.clearBottomActivities()
    }


    /**
     * 清除所有的activity
     */
    fun removeAllActivity() {
        AActivityStackUtils.removeAllActivity()
    }

    fun exitApp() {
        AActivityStackUtils.exitApp()
    }

}
