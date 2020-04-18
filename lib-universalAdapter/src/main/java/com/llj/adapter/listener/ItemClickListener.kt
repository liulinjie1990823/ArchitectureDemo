package com.llj.adapter.listener

import com.llj.adapter.UniversalAdapter
import com.llj.adapter.XViewHolder

/**
 * A unified interface for clicks on a [View] within each corresponding adapter.
 *
 * @param <Item>   The uniform item used for the [Holder]
 * @param <Holder> The holder for the uniform item type.
</Holder></Item> */
interface ItemClickListener<Item, Holder : XViewHolder> {
    fun onItemClicked(adapter: UniversalAdapter<Item, Holder>, item: Item?, holder: Holder, position: Int)
    fun onItemDoubleClicked(adapter: UniversalAdapter<Item, Holder>, item: Item?, holder: Holder, position: Int)
    fun onItemLongClicked(adapter: UniversalAdapter<Item, Holder>, item: Item?, holder: Holder, position: Int)
}