package com.llj.adapter.multitype

import android.view.ViewGroup
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.XViewHolder
import com.llj.adapter.model.TypeItem


/**
 * describe 多类型的Adapter
 *
 * @author liulinjie
 * @date 2022/3/31 4:54 下午
 */
class MultiTypeListAdapter : ListBasedAdapter<TypeItem?, XViewHolder>() {

  private val multiTypeManager = MultiTypeManager()

  override fun getItemViewType(position: Int): Int {
    return multiTypeManager.getItemViewType(get(position), position)
  }

  override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): XViewHolder {
    val listBasedAdapter = multiTypeManager.getAdapterByItemViewType(itemType)
    return listBasedAdapter!!.onCreateViewHolder(parent, itemType)
  }

  override fun onBindViewHolder(holder: XViewHolder, item: TypeItem?, position: Int) {
    val listBasedAdapter = multiTypeManager.getAdapterByItemViewType(holder.itemViewType)
    listBasedAdapter?.onBindViewHolder(holder, item, position)
  }

  override fun onBindViewHolder(holder: XViewHolder, item: TypeItem?, position: Int, payloads: List<*>) {
    val listBasedAdapter = multiTypeManager.getAdapterByItemViewType(holder.itemViewType)
    listBasedAdapter?.onBindViewHolder(holder, item, position, payloads)
  }


  public fun <Item : TypeItem?, Holder : XViewHolder> removeAdapter(adapter: ListBasedAdapter<Item?, Holder>) {
    multiTypeManager.removeAdapter(adapter)
  }

  public fun removeAdapter(itemViewType: Int) {
    multiTypeManager.removeAdapter(itemViewType)
  }

  public fun <Item : TypeItem?, Holder : XViewHolder> addAdapter(adapter: ListBasedAdapter<Item?, Holder>, defaultType: Boolean = false) {
    multiTypeManager.addAdapter(adapter, defaultType)
  }


}