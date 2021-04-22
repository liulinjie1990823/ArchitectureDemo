package com.llj.adapter.observable

import java.util.*

/**
 * Class which adapts an existing [List] implementation into an
 * [ObservableList]
 *
 * @param <T> The type of item that the list contains.
</T> */
class ObservableListWrapper<T> : ObservableList<T> {

  @JvmOverloads
  constructor(underlyingList: MutableList<T>? = LinkedList()) {
    var underlyingList = underlyingList
    if (underlyingList == null) underlyingList = LinkedList()
    this.underlyingList = underlyingList
    listObserver = SimpleListObserver()
  }

  /**
   * The list which is currently backing this adapter
   */
  private var underlyingList: MutableList<T>?
  private var listObserver: SimpleListObserver<Any?>
  private var runningTransaction = false
  private var transactionModified = false

  override fun getListObserver(): ListObserver<Any?> {
    return listObserver
  }

  /**
   * Sets the underlying list to be used as the contents of this adapter.
   * Note that modifying the passed list will modify the contents
   * of this [ObservableListWrapper], but changes will go unnoticed and
   * the events will not be raised. Perform modifications through this
   * [ObservableListWrapper] if the events need to be raised.
   *
   * @param list The list which will back this adapter
   */
  fun setList(list: MutableList<T>?) {
    underlyingList = list
    onGenericChange()
  }

  override fun replaceAll(elements: Collection<T>?) {
  }

  /**
   * Updates the underlying list to reflect the contents of the given `list`
   * by replacing elements which already exist and appending those that do not.
   *
   * @param list the data to update the underlying list with.
   */
  fun updateFromList(list: List<T>) {
    val updateFromList = ArrayList(list)

    // Loop through the current list and find duplicate entries.
    // Replace them in the current list while removing them from
    // the list that is being loaded.
    val it: ListIterator<T> = this.listIterator()
    while (it.hasNext()) {
      val nextIndex = it.nextIndex()
      val indexOfObjectInLoadList = updateFromList.indexOf(it.next())
      if (indexOfObjectInLoadList != -1) {
        set(nextIndex, updateFromList.removeAt(indexOfObjectInLoadList))
      }
    }

    // Add all remaining, non-duplicate, items
    addAll(updateFromList)
  }

  /**
   * Replaces any existing instances of the given item (as defined by)
   * or appends the item to the end of the list if not found.
   *
   * @param item       The item to add
   * @param replaceAll True to replace all instances of the item, false to only replace the first instance in the list.
   */
  fun addToListOrReplace(item: T?, replaceAll: Boolean) {
    var foundItem = false
    if (item == null) {
      return
    } else {
      val it: ListIterator<T> = this.listIterator()
      while (it.hasNext()) {
        val nextIndex = it.nextIndex()
        if (it.next() == item) {
          set(nextIndex, item)
          foundItem = if (replaceAll) {
            true
          } else {
            return
          }
        }
      }
    }
    if (!foundItem) {
      add(item)
    }
  }

  /**
   * Removes all items from this list and adds all the given items,
   * effectively replacing the entire list.
   *
   * @param contents The items to set as the new contents.
   */
  fun replaceContents(contents: Collection<T>?) {
    underlyingList!!.clear()
    underlyingList!!.addAll(contents!!)
    listObserver.notifyGenericChange()
  }

  override fun add(element: T): Boolean {
    val position = underlyingList!!.size
    val result = underlyingList!!.add(element)
    if (result) {
      onItemRangeChanged(position, 1)
    }
    return result
  }

  override fun add(index: Int, element: T) {
    underlyingList!!.add(index, element)
    onItemRangeInserted(index, 1)
  }

  override fun addAll(elements: Collection<T>): Boolean {
    var result = false
    val position = underlyingList!!.size
    if (elements != null) {
      result = underlyingList!!.addAll(elements)
    }
    if (result) {
      onItemRangeInserted(position, elements.size)
    }
    return result
  }

  override fun addAll(index: Int, elements: Collection<T>): Boolean {
    var result = false
    if (underlyingList != null) {
      result = underlyingList!!.addAll(index, elements)
    }
    if (result) {
      onItemRangeInserted(index, elements.size)
    }
    return result
  }

  override fun clear() {
    val count = underlyingList!!.size
    underlyingList!!.clear()
    onItemRangeRemoved(0, count)
  }

  override operator fun contains(element: T): Boolean {
    return underlyingList!!.contains(element)
  }

  override fun containsAll(elements: Collection<T>): Boolean {
    return underlyingList!!.containsAll(elements)
  }

  override fun get(index: Int): T {
    return underlyingList!![index]
  }

  override fun indexOf(element: T): Int {
    return underlyingList!!.indexOf(element)
  }

  override fun isEmpty(): Boolean {
    return underlyingList!!.isEmpty()
  }

  override fun iterator(): MutableIterator<T> {
    return underlyingList!!.iterator()
  }

  override fun lastIndexOf(element: T): Int {
    return underlyingList!!.lastIndexOf(element)
  }

  override fun listIterator(): MutableListIterator<T> {
    return underlyingList!!.listIterator()
  }

  override fun listIterator(index: Int): MutableListIterator<T> {
    return underlyingList!!.listIterator(index)
  }

  override fun removeAt(index: Int): T {
    val result = underlyingList!!.removeAt(index)
    onItemRangeRemoved(index, 1)
    return result
  }

  override fun remove(element: T): Boolean {
    val index = underlyingList!!.indexOf(element)
    if (index >= 0) {
      removeAt(index)
      return true
    }
    return false
  }

  override fun removeAll(elements: Collection<T>): Boolean {
    val result = underlyingList!!.removeAll(elements)
    if (result) {
      onGenericChange()
    }
    return result
  }

  override fun retainAll(elements: Collection<T>): Boolean {
    val result = underlyingList!!.retainAll(elements)
    if (result) {
      onGenericChange()
    }
    return result
  }

  override fun set(index: Int, element: T): T {
    val result = underlyingList!!.set(index, element)
    onItemRangeChanged(index, 1)
    return result
  }

  override val size: Int
    get() = underlyingList!!.size


  override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
    return underlyingList!!.subList(fromIndex, toIndex)
  }

  override fun toString(): String {
    return "Observable(" + underlyingList.toString() + ")"
  }

  protected fun onItemRangeChanged(startPosition: Int, itemCount: Int) {
    if (tryTransactionModification()) {
      listObserver.notifyItemRangeChanged(startPosition, itemCount)
    }
  }

  protected fun onItemRangeChanged(startPosition: Int, itemCount: Int, payload: Any?) {
    if (tryTransactionModification()) {
      listObserver.notifyItemRangeChanged(startPosition, itemCount, payload)
    }
  }

  protected fun onItemRangeInserted(startPosition: Int, itemCount: Int) {
    if (tryTransactionModification()) {
      listObserver.notifyItemRangeInserted(startPosition, itemCount)
    }
  }

  protected fun onItemRangeRemoved(startPosition: Int, itemCount: Int) {
    if (tryTransactionModification()) {
      listObserver.notifyItemRangeRemoved(startPosition, itemCount)
    }
  }

  protected fun onGenericChange() {
    if (tryTransactionModification()) {
      listObserver.notifyGenericChange()
    }
  }

  /**
   * Records a modification attempt to any currently running transaction and
   * returns whether the change should notify listeners.
   *
   * @return True if the modification should notify listeners, false if it
   * should not.
   */
  private fun tryTransactionModification(): Boolean {
    if (runningTransaction) {
      transactionModified = true
      return false
    }
    return true
  }

  override fun beginTransaction() {
    if (!runningTransaction) {
      runningTransaction = true
      transactionModified = false
    } else {
      throw IllegalStateException("Tried to begin a transaction when one was already running!")
    }
  }

  override fun endTransaction() {
    if (runningTransaction) {
      runningTransaction = false
      if (transactionModified) {
        onGenericChange()
      }
    } else {
      throw IllegalStateException("Tried to end a transaction when no transaction was running!")
    }
  }

}