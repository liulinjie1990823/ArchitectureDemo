package com.llj.adapter.observable

/**
 * Interface which listens to [ListObserver] changes.
 *
 * @param <T> The type of items stored in the observed list.
 */
interface ListObserverListener<out T> {

    fun onItemRangeChanged(observer: ListObserver<@UnsafeVariance T>, startPosition: Int, count: Int, payload: Any?)
    fun onItemRangeChanged(observer: ListObserver<@UnsafeVariance T>, startPosition: Int, count: Int)
    fun onItemRangeInserted(observer: ListObserver<@UnsafeVariance T>, startPosition: Int, count: Int)
    fun onItemRangeRemoved(observer: ListObserver<@UnsafeVariance T>, startPosition: Int, count: Int)
    fun onGenericChange(observer: ListObserver<@UnsafeVariance T>)
}