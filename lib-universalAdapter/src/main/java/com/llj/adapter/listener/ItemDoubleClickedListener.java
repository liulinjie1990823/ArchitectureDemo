package com.llj.adapter.listener;

import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.ViewHolder;

/**
 * PROJECT:CommonAdapter
 * DESCRIBE:
 * Created by llj on 2017/2/11.
 */

public interface ItemDoubleClickedListener<Item, Holder extends ViewHolder> {

    void onItemDoubleClicked(UniversalAdapter adapter, Item item, Holder holder, int position);
}