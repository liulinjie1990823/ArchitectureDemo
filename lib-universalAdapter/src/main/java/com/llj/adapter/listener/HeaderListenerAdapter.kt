package com.llj.adapter.listener

import com.llj.adapter.XViewHolder

/**
 *
 */
interface HeaderListenerAdapter<Item, Holder : XViewHolder> {
    fun setHeaderClickListener(headerClickListener: HeaderClickListener<Item, Holder>)
}