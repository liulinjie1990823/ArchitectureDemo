package com.llj.adapter

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/1/14.
 */
class MappableSet<T> {
    private val members: TransactionalHashSet<T> = TransactionalHashSet()

    fun getMembers(): Iterable<T> {
        return members
    }

    /**
     * Adds the given item to the set.
     *
     * @param item The item to add.
     * @param <E>  E
    </E> */
    fun <E : T> add(item: E) {
        members.add(item)
    }

    /**
     * Removes the given item from the set.
     *
     * @param item The item to remove.
     * @param <E>  E
     * @return True if the item was removed, false if it wasn't found in the
     * set.
    </E> */
    fun <E : T> remove(item: E): Boolean {
        return members.remove(item)
    }

    /**
     * Removes all items from this set.
     */
    fun clear() {
        members.clear()
    }

    /**
     * Returns true if the given item is found in the set.
     *
     * @param item The item to look for.
     * @param <E>  E
     * @return True if the item was found, false if it is not.
    </E> */
    operator fun <E : T> contains(item: E): Boolean {
        return members.contains(item)
    }

    /**
     * @return The number of items in this set.
     */
    fun size(): Int {
        return members.size
    }

    /**
     * Calls the given [Delegate] on all items in the set. Items may be
     * added or removed during this call, but changes may not be reflected
     * until this completes.
     *
     * @param function The [Delegate] to call for each item.
     */
    fun map(function: Delegate<T>) {
        beginTransaction()
        for (member in members) {
            function.execute(member)
        }
        endTransaction()
    }

    fun beginTransaction() {
        members.beginTransaction()
    }

    fun endTransaction() {
        members.endTransaction()
    }
}