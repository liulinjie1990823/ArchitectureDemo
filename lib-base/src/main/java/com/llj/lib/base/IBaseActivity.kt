package com.llj.lib.base

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.view.MotionEvent
import android.view.Window
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat

import com.llj.lib.utils.AInputMethodManagerUtils

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
interface IBaseActivity : IActivityStack {

    fun setTranslucentStatusBar(window: Window, textBlackColor: Boolean) {
        StatusBarCompat.translucentStatusBar(window, true)
        LightStatusBarCompat.setLightStatusBar(window, textBlackColor)
    }

    fun initLifecycleObserver(lifecycle: Lifecycle)

    fun superOnBackPressed()

    fun backToLauncher(nonRoot: Boolean)

    fun onTouchEvent(activity: Activity, event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_DOWN) {
            AInputMethodManagerUtils.hideSoftInputFromWindow(activity)
        }
    }

    fun moduleName(): String {
        return "app"
    }


}
