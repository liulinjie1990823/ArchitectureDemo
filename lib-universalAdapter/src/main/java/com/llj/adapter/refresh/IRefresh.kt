package com.llj.adapter.refresh

import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.ViewHolder

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/20
 */
interface IRefresh<Item, Holder : ViewHolder> : IPager {
    fun finishRefreshOrLoadMore(success: Boolean)

    fun handleData(hasNextPage: Boolean, list: Collection<Item>)

    fun size(): Int

    fun getAdapter(): ListBasedAdapter<Item, Holder>?
}