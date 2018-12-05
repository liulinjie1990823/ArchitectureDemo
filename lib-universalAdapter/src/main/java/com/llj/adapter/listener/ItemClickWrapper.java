package com.llj.adapter.listener;

import android.view.View;

import com.llj.adapter.UniversalConverter;
import com.llj.adapter.ViewHolder;
import com.llj.adapter.util.UniversalAdapterUtils;


/**
 * @param <Item>   Item
 * @param <Holder> Holder
 */
public class ItemClickWrapper<Item, Holder extends ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private UniversalConverter<Item, Holder> mUniversalConverter;

    public ItemClickWrapper(UniversalConverter<Item, Holder> universalConverter) {
        this.mUniversalConverter = universalConverter;
    }

    public void register(View view) {
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mUniversalConverter.getAdapter().onItemClicked(UniversalAdapterUtils.getIndex(v), v);
    }

    @Override
    public boolean onLongClick(View v) {
        return mUniversalConverter.getAdapter().onItemLongClicked(UniversalAdapterUtils.getIndex(v), v);
    }
}
