package com.llj.adapter.observable

import com.llj.adapter.Delegate
import com.llj.adapter.MappableSet

/**
 * Simple implementation of a [ListObserver].
 */
class SimpleListObserver<T : Any?> : ListObserver<T> {

    private val listeners: MappableSet<ListObserverListener<T>> = MappableSet()


    override fun addListener(listener: ListObserverListener<T>) {
        listeners.add(listener)
    }

    override fun removeListener(listener: ListObserverListener<T>): Boolean {
        return listeners.remove(listener)
    }

    /**
     * Call to notify listeners that the items in a given range were changed.
     *
     * @param startPosition The position of the first element that changed.
     * @param itemCount How many sequential items were changed.
     */
    fun notifyItemRangeChanged(startPosition: Int, itemCount: Int) {
        mapListeners(object : Delegate<ListObserverListener<T>> {
            override fun execute(params: ListObserverListener<T>) {
                params.onItemRangeChanged(this@SimpleListObserver, startPosition, itemCount)
            }
        })
    }

    fun notifyItemRangeChanged(startPosition: Int, itemCount: Int, payload: Any?) {
        mapListeners(object : Delegate<ListObserverListener<T>> {
            override fun execute(params: ListObserverListener<T>) {
                params.onItemRangeChanged(this@SimpleListObserver, startPosition, itemCount, payload)
            }
        })
    }

    /**
     * Call to notify listeners that items were inserted at a given range.
     *
     * @param startPosition The position of the first element that was inserted.
     * @param itemCount How many sequential items were inserted.
     */
    fun notifyItemRangeInserted(startPosition: Int, itemCount: Int) {
        mapListeners(object : Delegate<ListObserverListener<T>> {
            override fun execute(params: ListObserverListener<T>) {
                params.onItemRangeInserted(this@SimpleListObserver, startPosition, itemCount)
            }
        })
    }

    /**
     * Call to notify listeners that items were removed at a given range.
     *
     * @param startPosition The position of the first element that was removed.
     * @param itemCount How many sequential items were removed.
     */
    fun notifyItemRangeRemoved(startPosition: Int, itemCount: Int) {
        mapListeners(object : Delegate<ListObserverListener<T>> {
            override fun execute(params: ListObserverListener<T>) {
                params.onItemRangeRemoved(this@SimpleListObserver, startPosition, itemCount)
            }
        })
    }

    private val genericChangeDelegate: Delegate<ListObserverListener<T>> = object : Delegate<ListObserverListener<T>> {
        override fun execute(params: ListObserverListener<T>) {
            params.onGenericChange(this@SimpleListObserver)
        }
    }

    /**
     * Call to notify listeners that a generic change happened which changed the list contents.
     */
    fun notifyGenericChange() {
        mapListeners(genericChangeDelegate)
    }

    private fun mapListeners(function: Delegate<ListObserverListener<T>>) {
        listeners.beginTransaction()
        listeners.map(function)
        listeners.endTransaction()
    }


}