package com.llj.adapter.listener

import com.llj.adapter.UniversalAdapter
import com.llj.adapter.XViewHolder

interface FooterClickListener<Item, Holder : XViewHolder> {
    fun onFooterClicked(adapter: UniversalAdapter<Item, Holder>, item: Item?, footerHolder: Holder, position: Int)
    fun onFooterLongClicked(adapter: UniversalAdapter<Item, Holder>, item: Item?, footerHolder: Holder, position: Int)
    fun onFooterDoubleClicked(adapter: UniversalAdapter<Item, Holder>, item: Item?, footerHolder: Holder, position: Int)
}