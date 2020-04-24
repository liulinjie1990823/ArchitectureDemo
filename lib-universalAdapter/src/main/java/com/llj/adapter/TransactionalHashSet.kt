package com.llj.adapter

import java.util.*

class TransactionalHashSet<T> : HashSet<T> {
  private var toAdd: HashSet<T>? = null
  private var toRemove: HashSet<T>? = null

  @Volatile
  private var inTransaction = false

  constructor() : super() {
    init()
  }

  constructor(collection: Collection<T>?) : super(collection) {
    init()
  }

  private fun init() {
    inTransaction = false
    toAdd = HashSet()
    toRemove = HashSet()
  }

  /**
   * Starts a transaction. This causes additions and removals to be stored, but they will not be
   * performed until a call to [.endTransaction]
   */
  fun beginTransaction() {
    synchronized(this) { inTransaction = true }
  }

  /**
   * Commits all pending operations and returns the [HashSet] to a normal state where
   * operations are committed immediately.
   */
  fun endTransaction() {
    synchronized(this) {
      // Commit the transaction if we're in one
      if (inTransaction) {
        // Lower the flag so we actually commit operations
        inTransaction = false

        // Add all the items we need to add
        for (item in toAdd!!) {
          add(item)
        }

        // Remove all the items we need to remove
        for (item in toRemove!!) {
          remove(item)
        }
        toAdd!!.clear()
        toRemove!!.clear()
      }
    }
  }

  override fun add(element: T): Boolean {
    synchronized(this) {
      return if (inTransaction) {
        // If we're in a transaction, add it to the toAdd list
        toAdd!!.add(element)
        // The item already exists if it's in this set or the add set
        val exists = contains(element) || toAdd!!.contains(element)
        // Since this is happening later in the transaction, undo any
        // removal
        val wasToBeRemoved = toRemove!!.remove(element)

        // If it was to be removed, we just added it back
        if (wasToBeRemoved) {
          true
        } else {
          !exists
        }
      } else {
        // Not in a transaction, proceed as normal
        super.add(element)
      }
    }
  }

  override fun addAll(elements: Collection<T>): Boolean {
    // Take the lock here so we're not acquiring/releasing it repeatedly
    synchronized(this) { return super.addAll(elements) }
  }


  override fun remove(element: T): Boolean {
    synchronized(this) {
      return if (inTransaction) {
        // If the remove list already contains it, we aren't doing
        // anything
        if (toRemove!!.contains(element)) {
          false
        } else {
          // If it's in either set, we're doing a removal
          if (toAdd!!.remove(element) || contains(element)) {
            toRemove!!.add(element)
            true
          } else {
            false
          }
        }
      } else {
        // Not in a transaction, proceed as normal
        super.remove(element)
      }
    }
  }

  override fun removeAll(elements: Collection<T>): Boolean {
    // Take the lock here so we're not acquiring/releasing it repeatedly
    synchronized(this) { return super.removeAll(elements) }
  }

  override fun clear() {
    removeAll(this)
  }

  companion object {
    private const val serialVersionUID = -7093089056781106409L
  }
}