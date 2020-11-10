package com.llj.lib.base

import android.app.Activity
import android.graphics.Insets
import android.view.*
import androidx.lifecycle.Lifecycle
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat
import com.llj.lib.utils.AInputMethodManagerUtils

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/24
 */
interface IBaseActivity : IActivityStack, IModuleName {

  fun initLifecycleObserver(lifecycle: Lifecycle)

  fun superOnBackPressed()

  fun backToLauncher(nonRoot: Boolean)

  fun onTouchEvent(activity: Activity, event: MotionEvent) {
    if (event.action == MotionEvent.ACTION_DOWN) {
      AInputMethodManagerUtils.hideSoftInputFromWindow(activity)
    }
  }

}
