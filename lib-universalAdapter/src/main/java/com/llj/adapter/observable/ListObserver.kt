package com.llj.adapter.observable

/**
 * describe 观察者列表
 *
 * @author liulinjie
 * @date 2020/4/18 12:28 AM
 */
interface ListObserver<Item> {
    fun addListener(listener: ListObserverListener<Item>)
    fun removeListener(listener: ListObserverListener<Item>): Boolean
}