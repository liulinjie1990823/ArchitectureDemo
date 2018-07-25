package com.llj.adapter.listener;

import android.view.View;

import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.ViewHolder;


/**
 * A unified interface for clicks on a {@link View} within each corresponding adapter.
 *
 * @param <Item>   The uniform item used for the {@link Holder}
 * @param <Holder> The holder for the uniform item type.
 */
public interface ItemClickedListener<Item, Holder extends ViewHolder> {
    void onItemClicked(UniversalAdapter adapter, Item item, Holder holder, int position);

}
