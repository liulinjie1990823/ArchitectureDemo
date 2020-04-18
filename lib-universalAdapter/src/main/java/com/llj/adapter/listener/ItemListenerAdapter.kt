package com.llj.adapter.listener

import com.llj.adapter.XViewHolder

/**
 *
 */
interface ItemListenerAdapter<Item, Holder : XViewHolder> {
    fun setItemClickedListener(listener: ItemClickListener<Item, Holder>)
}