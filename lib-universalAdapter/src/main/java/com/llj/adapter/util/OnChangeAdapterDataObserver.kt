package com.llj.adapter.util

import androidx.recyclerview.widget.RecyclerView

/**
 * OnChangeAdapterDataObserver
 *
 * @author liulinjie
 * @date 2022-05-13 12:49
 */
open class OnChangeAdapterDataObserver : RecyclerView.AdapterDataObserver() {

  override fun onChanged() {
  }

  override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
    onChanged()
  }

  override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
    onChanged()
  }
}