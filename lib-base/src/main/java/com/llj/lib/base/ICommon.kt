package com.llj.lib.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

/**
 * ArchitectureDemo
 * describe:通用的方法
 * @author llj
 * @date 2018/5/24
 */
interface ICommon<V : ViewBinding> : IWindowInset, IReflectView<V> {

  fun getIntentData(intent: Intent) {}


  fun getArgumentsData(bundle: Bundle?) {}

  //返回Boolean说明是否预加载数据
  fun preLoadData(): Boolean {
    return false
  }

  fun layoutView(): View? {
    return null
  }

  @LayoutRes
  fun layoutId(): Int

  fun initViews(savedInstanceState: Bundle?)

  fun initData()
}
