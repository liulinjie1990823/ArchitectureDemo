package com.llj.adapter.listener;

import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.ViewHolder;

public interface ItemLongClickedListener<Item, Holder extends ViewHolder> {
    boolean onItemLongClicked(UniversalAdapter<Item, Holder> adapter, Item item, Holder holder, int position);
}
