package com.llj.adapter

import com.llj.adapter.model.ISelect
import com.llj.adapter.observable.ListObserver
import com.llj.adapter.observable.ObservableList

/**
 * PROJECT:UniversalAdapter
 *
 * DESCRIBE:
 *
 * ObservableList：实现该接口以封装对集合的操作
 *
 * @Created by llj on 2017/2/10.
 */
abstract class ListBasedAdapter<Item, Holder : XViewHolder> : UniversalAdapter<Item?, Holder>, ObservableList<Item?> {

  private lateinit var mList: MutableList<Item?>

  var clickPosition = 0

  constructor() : super() {
    setItemsList(null)
  }

  constructor(list: MutableList<Item?>) : super() {
    setItemsList(list)
  }


  val clickItem: Item?
    get() = if (size > clickPosition) {
      get(clickPosition)
    } else null


  fun setItemsList(list: ObservableList<Item?>) {
    list.getListObserver().addListener(observableListener)
    setItemsList(list as MutableList<Item?>)
  }


  fun unbindList() {
    if (mList is ObservableList<Item?>) {
      (mList as ObservableList<Item?>).getListObserver().removeListener(observableListener)
    }
  }

  fun setItemsList(list: MutableList<Item?>?) {
    var listNew = list
//    unbindList()
    if (listNew == null) {
      listNew = ArrayList()
    }
    mList = listNew
    notifyDataSetChanged()
  }

  fun getList(): MutableList<Item?> {
    return mList
  }

  override fun getListObserver(): ListObserver<Any?> {
    return mListObserver
  }

  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////

  //List
  override val size: Int
    get() = mList.size


  override fun getCount(): Int {
    return mList.size
  }

  //List
  override fun isEmpty(): Boolean {
    return mList.isEmpty()
  }

  private fun isCollectionEmpty(list: Collection<Item?>?): Boolean {
    return list == null || list.isEmpty()
  }

  //region  ObservableList<Item?>
  override fun add(index: Int, element: Item?) {
    mList.add(index, element)
    onItemRangeInserted(index, 1)
  }

  override fun add(element: Item?): Boolean {
    val location = mList.size
    val result = mList.add(element)
    onItemRangeInserted(location, 1)
    return result
  }

  override fun addAll(elements: Collection<Item?>): Boolean {
    if (isCollectionEmpty(elements)) {
      return false
    }
    val location = mList.size
    if (mList.addAll(elements)) {
      onItemRangeInserted(location, elements.size)
      return true
    }
    return false
  }

  override fun addAll(index: Int, elements: Collection<Item?>): Boolean {
    if (isCollectionEmpty(elements)) {
      return false
    }
    if (mList.addAll(index, elements)) {
      onItemRangeInserted(index, elements.size)
      return true
    }
    return false
  }

  override fun removeAt(index: Int): Item? {
    val result = mList.removeAt(index)
    onItemRangeRemoved(index, 1)
    return result
  }

  override fun remove(element: Item?): Boolean {
    val location = mList.indexOf(element)
    if (location >= 0) {
      removeAt(location)
      return true
    }
    return false
  }

  override fun removeAll(elements: Collection<Item?>): Boolean {
    val result = mList.removeAll(elements)
    if (result) {
      onGenericChange()
    }
    return result
  }

  override fun retainAll(elements: Collection<Item?>): Boolean {
    val result = mList.retainAll(elements)
    if (result) {
      onGenericChange()
    }
    return result
  }

  override fun clear() {
    val count = size
    mList.clear()
    onItemRangeRemoved(0, count)
  }

  override fun set(index: Int, element: Item?): Item? {
    val result = mList.set(index, element)
    if (result != element) {
      onItemRangeChanged(index, 1)
    }
    return result
  }
  //endregion


  //region List<Item?>
  override fun get(index: Int): Item? {
    if (index >= mList.size) {
      return null
    }
    return mList[index]
  }

  override operator fun contains(element: Item?): Boolean {
    return mList.contains(element)
  }

  override fun containsAll(elements: Collection<Item?>): Boolean {
    return mList.containsAll(elements)
  }

  override fun indexOf(element: Item?): Int {
    return mList.indexOf(element)
  }

  override fun lastIndexOf(element: Item?): Int {
    return mList.lastIndexOf(element)
  }

  override fun iterator(): MutableIterator<Item?> {
    return mList.iterator()
  }

  override fun listIterator(): MutableListIterator<Item?> {
    return mList.listIterator()
  }

  override fun listIterator(index: Int): MutableListIterator<Item?> {
    return mList.listIterator()
  }

  override fun subList(fromIndex: Int, toIndex: Int): MutableList<Item?> {
    return mList.subList(fromIndex, toIndex)
  }
  //endregion

  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////

  override fun notifyDataSetChanged() {
    onGenericChange()
  }

  //获取最后一个item
  fun getLast(): Item? {
    if (getList().isEmpty()) {
      return null
    }
    return getList()[size - 1]

  }

  //移除最后一个item
  fun removeLast() {
    if (getList().isEmpty()) {
      return
    }
    getList().removeAt(size - 1)
  }

  //获取选中项
  val selectItem: ISelect?
    get() {
      val itemsList = getList()
      if (isCollectionEmpty(itemsList)) {
        return null
      }
      for (item in itemsList) {
        if (item == null) {
          continue
        }
        if (item is ISelect && (item as ISelect).isSelect) {
          return item
        }
      }
      return null
    }

}