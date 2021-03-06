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

  //设置透明状态栏
  fun setTranslucentStatusBar(window: Window, textBlackColor: Boolean = true) {
    StatusBarCompat.translucentStatusBar(window, true)
    LightStatusBarCompat.setLightStatusBar(window, textBlackColor)
  }

  //设置透明状态栏和底部栏
  fun setTranslucentAll(window: Window?, textBlackColor: Boolean = true) {
    StatusBarCompat.translucentStatusBarAndNavigation(window!!, true)
    LightStatusBarCompat.setLightStatusBar(window, textBlackColor)
  }


  //将insets事件分发到ViewPager里面
  fun dispatchApplyWindowInsets(view: ViewGroup) {
    view.setOnApplyWindowInsetsListener { _, insets ->
      val childCount = view.childCount
      for (index in 0 until childCount) {
        view.getChildAt(index).dispatchApplyWindowInsets(insets)
      }
      insets
    }
  }

  //状态栏添加padding，根据android:fitsSystemWindows="true"标记
  fun applyStatusBarInsets(view: View) {
    view.setOnApplyWindowInsetsListener { v: View, insets: WindowInsets ->
      val insetsBottom = insets
          .replaceSystemWindowInsets(0, insets.systemWindowInsetTop, 0, 0)
      v.onApplyWindowInsets(insetsBottom)
    }
  }

  //底部栏添加padding，根据android:fitsSystemWindows="true"标记
  fun applyNavigationInsets(view: View, needDispatch: Boolean = true) {
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