package com.llj.lib.base

import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsets
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat

/**
 * describe 处理透明模式下的WindowInset
 *
 * @author liulinjie
 * @date 2020/11/10 10:58 AM
 */
interface IWindowInset {

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
      view.setOnApplyWindowInsetsListener(object : View.OnApplyWindowInsetsListener {
        override fun onApplyWindowInsets(v: View, insets: WindowInsets): WindowInsets {
          val viewGroup = v as ViewGroup
          if (needDispatch) {
            val insetsTop = insets.replaceSystemWindowInsets(0, insets.systemWindowInsetTop, 0, 0)
            val childCount = viewGroup.childCount
            for (i in 0 until childCount) {
              viewGroup.getChildAt(i).dispatchApplyWindowInsets(insetsTop)
            }
          }
          val insetsBottom = insets.replaceSystemWindowInsets(0, 0, 0, insets.systemWindowInsetBottom)
          //当前viewGroup必须设置fitSystemWindow，否则无法消费，则会继续分发到子view
          return viewGroup.onApplyWindowInsets(insetsBottom)
        }
      })
    } else
      view.setOnApplyWindowInsetsListener(object : View.OnApplyWindowInsetsListener {
        override fun onApplyWindowInsets(v: View, insets: WindowInsets): WindowInsets {
          val insetsBottom = insets.replaceSystemWindowInsets(0, 0, 0, insets.systemWindowInsetBottom)
          return v.onApplyWindowInsets(insetsBottom)
        }
      })
  }

}