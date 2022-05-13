package com.llj.adapter.listener

import android.view.View
import android.view.View.OnLongClickListener
import com.llj.adapter.UniversalConverter
import com.llj.adapter.XViewHolder
import com.llj.adapter.util.UniversalAdapterUtils

/**
 * ItemClickWrapper
 *
 * [com.llj.adapter.converter.PagerAdapterConverter]
 * [com.llj.adapter.converter.ViewGroupAdapterConverter]
 *
 * @author liulinjie
 * @date 2017/2/11
 */
class ItemClickWrapper<Item, Holder : XViewHolder>(
  private val mUniversalConverter: UniversalConverter<Item, Holder>,
) : View.OnClickListener, OnLongClickListener {


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