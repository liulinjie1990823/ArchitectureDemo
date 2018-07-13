package com.llj.lib.net.observer

import io.reactivex.disposables.Disposable

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/10
 */
interface IObserverTag : ITag {

    fun getDisposable() :Disposable
}
