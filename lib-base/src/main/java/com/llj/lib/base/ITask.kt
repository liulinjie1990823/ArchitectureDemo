package com.llj.lib.base

import io.reactivex.disposables.Disposable

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/5
 */
interface ITask {

    fun addDisposable(tag: Any, disposable: Disposable)

    fun removeDisposable(tag: Any?)

    fun removeAllDisposable()
}
