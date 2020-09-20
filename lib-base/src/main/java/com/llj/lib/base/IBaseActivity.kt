package com.llj.lib.base

import android.app.Activity
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

  fun setTranslucentStatusBar(window: Window, textBlackColor: Boolean) {
    StatusBarCompat.translucentStatusBar(window, true)
    LightStatusBarCompat.setLightStatusBar(window, textBlackColor)
  }

  fun dispatchApplyWindowInsets(view: ViewGroup) {
    view.setOnApplyWindowInsetsListener { v, insets ->
      val childCount = view.childCount
      for (index in 0 until childCount) {
        view.getChildAt(index).dispatchApplyWindowInsets(insets)
      }
      insets
    }
  }

  fun applyStatusBarInsets(view: View) {
    view.setOnApplyWindowInsetsListener { v: View, insets: WindowInsets ->
      val insetsBottom = insets
          .replaceSystemWindowInsets(0, insets.systemWindowInsetTop, 0, 0)
      v.onApplyWindowInsets(insetsBottom)
    }
  }

  fun applyNavigationInsets(view: View) {
    applyNavigationInsets(view, true)
  }

  fun applyNavigationInsets(view: View, needDispatch: Boolean) {
    if (view is ViewGroup) {
      view.setOnApplyWindowInsetsListener(View.OnApplyWindowInsetsListener { v, insets ->
          val viewGroup = v as ViewGroup
          if (needDispatch) {
              val insetsTop = insets
                  .replaceSystemWindowInsets(0, insets.systemWindowInsetTop, 0, 0)
              val childCount = viewGroup.childCount
              for (i in 0 until childCount) {
                  viewGroup.getChildAt(i).dispatchApplyWindowInsets(insetsTop)
              }
          }
          val insetsBottom = insets
              .replaceSystemWindowInsets(0, 0, 0, insets.systemWindowInsetBottom)
          viewGroup.onApplyWindowInsets(insetsBottom)
      })
    } else
      view.setOnApplyWindowInsetsListener(object : View.OnApplyWindowInsetsListener {
          override fun onApplyWindowInsets(v: View, insets: WindowInsets): WindowInsets {
              val insetsBottom = insets.replaceSystemWindowInsets(0, 0, 0, insets.systemWindowInsetBottom)
              return v.onApplyWindowInsets(insetsBottom)
          }
      })
  }


  fun initLifecycleObserver(lifecycle: Lifecycle)

  fun superOnBackPressed()

  fun backToLauncher(nonRoot: Boolean)

  fun onTouchEvent(activity: Activity, event: MotionEvent) {
    if (event.action == MotionEvent.ACTION_DOWN) {
      AInputMethodManagerUtils.hideSoftInputFromWindow(activity)
    }
  }

}
