package com.llj.adapter.multitype

import androidx.collection.SparseArrayCompat
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.XViewHolder
import com.llj.adapter.model.TypeItem

/**
 * describe
 *
 * @author liulinjie
 * @date 2022-04-01 17:34
 */
class MultiTypeManager {

  private var mTypeAdapters = SparseArrayCompat<ListBasedAdapter<*, *>>()
  private var mDefaultItemViewType = UN_SET_ITEM_VIEW_TYPE

  companion object {
    const val UN_SET_ITEM_VIEW_TYPE = -10086
  }

  fun <Item : TypeItem?, Holder : XViewHolder> removeAdapter(adapter: ListBasedAdapter<Item?, Holder>) {
    val indexOfValue = mTypeAdapters.indexOfValue(adapter)
    if (indexOfValue >= 0) {
      mTypeAdapters.removeAt(indexOfValue)
    }
  }

  fun removeAdapter(itemViewType: Int) {
    val indexOfKey = mTypeAdapters.indexOfKey(itemViewType)
    if (indexOfKey >= 0) {
      mTypeAdapters.removeAt(indexOfKey)
    }
  }


  fun <Item : TypeItem?, Holder : XViewHolder> addAdapter(adapter: ListBasedAdapter<Item?, Holder>, defaultType: Boolean = false) {
    val size = mTypeAdapters.size()
    mTypeAdapters.put(size, adapter)
    if (defaultType) {
      mDefaultItemViewType = size
    }
  }

  fun <Item : TypeItem?, Holder : XViewHolder> addAdapter(itemViewType: Int, adapter: ListBasedAdapter<Item?, Holder>, defaultType: Boolean = false) {
    mTypeAdapters.put(itemViewType, adapter)
    if (defaultType) {
      mDefaultItemViewType = itemViewType
    }
  }

  fun getItemViewType(item: TypeItem?, position: Int): Int {
    val size = mTypeAdapters.size()
    for (index in 0 until size) {
      val value = mTypeAdapters.valueAt(index)
      //如果当前item找到了对应的ItemViewType
      val canFindItemViewType = value.canFindItemViewType(item, position)
      if (canFindItemViewType) {
        return mTypeAdapters.keyAt(index)
      }
    }

    if (mDefaultItemViewType == UN_SET_ITEM_VIEW_TYPE) {
      throw IllegalArgumentException(
        "no itemViewAdapter added that matches position=$position in data source"
      )
    }
    return mDefaultItemViewType
  }

  fun getAdapterByItemViewType(itemViewType: Int): ListBasedAdapter<TypeItem, XViewHolder>? {
    return mTypeAdapters[itemViewType] as ListBasedAdapter<TypeItem, XViewHolder>?
  }


}