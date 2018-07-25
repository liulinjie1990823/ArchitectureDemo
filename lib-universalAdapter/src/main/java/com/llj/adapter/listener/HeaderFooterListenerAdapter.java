package com.llj.adapter.listener;

import com.llj.adapter.ViewHolder;

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/1/15.
 */

public interface HeaderFooterListenerAdapter<Item, Holder extends ViewHolder> extends HeaderListenerAdapter, FooterListenerAdapter, ItemListenerAdapter<Item, Holder> {

}
