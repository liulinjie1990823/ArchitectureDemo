package com.llj.lib.base

import android.arch.lifecycle.Lifecycle

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
interface IFragment :IFragmentLazy{
    fun initLifecycleObserver(lifecycle: Lifecycle)

    fun moduleName(): String {
        return "app"
    }
}
