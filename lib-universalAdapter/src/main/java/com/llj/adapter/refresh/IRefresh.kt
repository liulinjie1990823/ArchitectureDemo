package com.llj.adapter.refresh

import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.XViewHolder

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/20
 */
interface IRefresh<Item, Holder : XViewHolder> : IPager {
  fun finishRefreshOrLoadMore(success: Boolean)

  fun finishRefreshOrLoadMore(success: Boolean, hasNextPage: Boolean)

  fun handleData(hasNextPage: Boolean, list: Collection<Item>?)
  fun handleData(shouldSetEnableLoadMore: Boolean, hasNextPage: Boolean, list: Collection<Item>?)

  fun size(): Int

  fun getAdapter(): ListBasedAdapter<Item, Holder>?
}