package com.llj.adapter.listener

import com.llj.adapter.UniversalAdapter
import com.llj.adapter.XViewHolder

interface HeaderClickListener<Item, Holder : XViewHolder> {
    fun onHeaderClicked(adapter: UniversalAdapter<Item, Holder>, item: Item?, headerHolder: Holder, position: Int)
    fun onHeaderDoubleClicked(adapter: UniversalAdapter<Item, Holder>, item: Item?, headerHolder: Holder, position: Int)
    fun onHeaderLongClicked(adapter: UniversalAdapter<Item, Holder>, item: Item?, footerHolder: Holder, position: Int)
}