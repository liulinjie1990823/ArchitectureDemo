package com.llj.adapter.listener

import com.llj.adapter.XViewHolder

/**
 *
 */
interface FooterListenerAdapter<Item, Holder : XViewHolder> {
    fun setFooterClickListener(footerClickListener: FooterClickListener<Item, Holder>)
}