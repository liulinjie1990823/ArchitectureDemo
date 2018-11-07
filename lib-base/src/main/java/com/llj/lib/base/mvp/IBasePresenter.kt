package com.llj.lib.base.mvp

import android.arch.lifecycle.DefaultLifecycleObserver

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
interface IBasePresenter : DefaultLifecycleObserver {
    fun destroy()
}
