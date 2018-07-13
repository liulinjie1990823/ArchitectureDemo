package com.llj.lib.base

import android.arch.lifecycle.Lifecycle

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
interface IFragment {
    fun initLifecycleObserver(lifecycle: Lifecycle)
}
