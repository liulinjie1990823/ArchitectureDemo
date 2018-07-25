package com.llj.adapter.listener;

import android.view.View;

import com.llj.adapter.CommonConverter;
import com.llj.adapter.ViewHolder;
import com.llj.adapter.util.UniversalAdapterUtils;


/**
 * @param <Item>   Item
 * @param <Holder> Holder
 */
public class ItemClickWrapper<Item, Holder extends ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private CommonConverter<Item, Holder> mCommonConverter;

    public ItemClickWrapper(CommonConverter<Item, Holder> commonConverter) {
        this.mCommonConverter = commonConverter;
    }

    public void register(View view) {
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mCommonConverter.getAdapter().onItemClicked(UniversalAdapterUtils.getIndex(v), v);
    }

    @Override
    public boolean onLongClick(View v) {
        return mCommonConverter.getAdapter().onItemLongClicked(UniversalAdapterUtils.getIndex(v), v);
    }
}
