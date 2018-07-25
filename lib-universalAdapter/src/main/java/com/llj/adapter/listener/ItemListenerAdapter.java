package com.llj.adapter.listener;

import com.llj.adapter.ViewHolder;

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/1/15.
 */

public interface ItemListenerAdapter<Item, Holder extends ViewHolder> {
    void setItemClickedListener(ItemClickedListener<Item, Holder> listener);

    void setItemDoubleClickedListener(ItemDoubleClickedListener<Item, Holder> listener);

    void setItemLongClickedListener(ItemLongClickedListener<Item, Holder> longClickedListener);
}
