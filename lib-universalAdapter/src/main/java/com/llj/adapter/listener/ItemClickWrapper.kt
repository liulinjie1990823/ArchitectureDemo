package com.llj.adapter.listener

import android.view.View
import android.view.View.OnLongClickListener
import com.llj.adapter.UniversalConverter
import com.llj.adapter.XViewHolder
import com.llj.adapter.util.UniversalAdapterUtils

/**
 * @param <Item> Item
 * @param <Holder> Holder
</Holder></Item> */
class ItemClickWrapper<Item, Holder : XViewHolder> : View.OnClickListener, OnLongClickListener {

    private val mUniversalConverter: UniversalConverter<Item, Holder>

    constructor(mUniversalConverter: UniversalConverter<Item, Holder>) {
        this.mUniversalConverter = mUniversalConverter
    }

    fun register(view: View) {
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
    }

    override fun onClick(v: View) {
        mUniversalConverter.getAdapter().onItemClicked(UniversalAdapterUtils.getIndex(v), v)
    }

    override fun onLongClick(v: View): Boolean {
        return mUniversalConverter.getAdapter().onItemLongClicked(UniversalAdapterUtils.getIndex(v), v)
    }

}