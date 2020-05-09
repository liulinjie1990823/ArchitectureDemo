package com.llj.lib.base

import androidx.lifecycle.Lifecycle

/**
 * ArchitectureDemo
 * describe:
 * @author liulj
 * @date 2018/5/24
 */
interface IBaseFragment : IBaseFragmentLazy {

  fun initLifecycleObserver(lifecycle: Lifecycle)

  fun getModuleName(): String {
    return "app"
  }
}
