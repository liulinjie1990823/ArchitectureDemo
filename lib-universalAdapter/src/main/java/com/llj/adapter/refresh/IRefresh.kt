package com.llj.adapter.refresh

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/20
 */
interface IRefresh<Item> : IPager {
    fun finishRefreshOrLoadMore(success: Boolean)

    fun handleData(hasNextPage: Boolean, list: Collection<Item>)

    fun size():Int
}