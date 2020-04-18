package com.llj.adapter.observable

/**
 * Interface for a [List] implementation which is also observable via a
 * [ListObserver].
 *
 * @param <T> The type of data in the list.
</T> */
interface ObservableList<T> : MutableList<T> {
    /**
     * Gets a [ListObserver] which can be used to be notified of changes
     * to the list data.
     *
     * @return The [ListObserver].
     */
    fun getListObserver(): ListObserver<Any?>

    /**
     * Begins a transaction. Changes will be visible immediately, but the data
     * changed events will not be raised until a call is made to
     * [.endTransaction].
     *
     * @throws IllegalStateException if a transaction is already running.
     */
    fun beginTransaction()

    /**
     * Ends the current transaction. This will raise the data changed events
     * if any modifications have been made.
     *
     * @throws IllegalStateException if no transaction is currently running.
     */
    fun endTransaction()
}