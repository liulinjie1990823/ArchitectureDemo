package com.llj.adapter.observable

/**
 * describe 用于PagerAdapterConverter刷新，统一用onGenericChange处理
 *
 * @author liulinjie
 * @date 2020/4/18 12:27 AM
 */
abstract class SimpleListObserverListener<T> : ListObserverListener<T> {
    override fun onItemRangeChanged(observer: ListObserver<T>, startPosition: Int, count: Int,
                                    payload: Any?) {
        onGenericChange(observer)
    }

    override fun onItemRangeChanged(observer: ListObserver<T>, startPosition: Int, count: Int) {
        onGenericChange(observer)
    }

    override fun onItemRangeInserted(observer: ListObserver<T>, startPosition: Int, count: Int) {
        onGenericChange(observer)
    }

    override fun onItemRangeRemoved(observer: ListObserver<T>, startPosition: Int, count: Int) {
        onGenericChange(observer)
    }
}