package com.llj.adapter

/**
 * describe 适配器基类
 *
 * @author liulinjie
 * @date 2020/4/18 12:20 AM
 */
interface UniversalConverter<Item, Holder : XViewHolder> {
    fun setAdapter(listAdapter: UniversalAdapter<Item, Holder>)
    fun getAdapter(): UniversalAdapter<Item, Holder>
    fun cleanup()
}