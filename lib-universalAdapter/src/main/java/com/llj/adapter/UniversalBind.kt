package com.llj.adapter

import androidx.appcompat.widget.*
import android.view.ViewGroup
import com.llj.adapter.converter.UniversalConverterFactory

/**
 * MerchantCenter
 * describe:
 * author llj
 * date 2018/7/16
 */
class UniversalBind<Item, Holder : ViewHolder, T : ListBasedAdapter<Item, Holder>>(builder: UniversalBind.Builder<Item, Holder, T>) {
    private val adapter: T
    private val viewGroup: ViewGroup

    init {
        adapter = builder.mAdapter
        viewGroup = builder.mViewGroup
        UniversalConverterFactory.createGeneric(adapter, viewGroup)
    }

    fun getAdapter():T{
        return adapter
    }


    class Builder<Item, Holder : ViewHolder, T : ListBasedAdapter<Item, Holder>> {

        val mViewGroup: ViewGroup
        val mAdapter: T

        constructor(mViewGroup: ViewGroup, mAdapter: T) {
            this.mViewGroup = mViewGroup
            this.mAdapter = mAdapter
        }


        fun setLayoutManager(layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager): Builder<Item, Holder, T> {
            if (mViewGroup is androidx.recyclerview.widget.RecyclerView) {
                mViewGroup.layoutManager = layoutManager
            }
            return this
        }

        fun setLinearLayoutManager(): Builder<Item, Holder, T> {
            if (mViewGroup is androidx.recyclerview.widget.RecyclerView) {
                mViewGroup.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(null)
            }
            return this
        }

        fun setLinearLayoutManager(orientation: Int): Builder<Item, Holder, T> {
            if (mViewGroup is androidx.recyclerview.widget.RecyclerView) {
                mViewGroup.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(null, orientation, false)
            }
            return this
        }

        fun setGridLayoutManager(spanCount: Int): Builder<Item, Holder, T> {
            if (mViewGroup is androidx.recyclerview.widget.RecyclerView) {
                mViewGroup.layoutManager = androidx.recyclerview.widget.GridLayoutManager(null, spanCount)
            }
            return this
        }

        fun setStaggeredGridLayoutManager(spanCount: Int, orientation: Int): Builder<Item, Holder, T> {
            if (mViewGroup is androidx.recyclerview.widget.RecyclerView) {
                mViewGroup.layoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager(spanCount, orientation)
            }
            return this
        }

        fun addItemDecoration(dividerItemDecoration: androidx.recyclerview.widget.DividerItemDecoration): Builder<Item, Holder, T> {
            if (mViewGroup is androidx.recyclerview.widget.RecyclerView) {
                mViewGroup.addItemDecoration(dividerItemDecoration)
            }
            return this
        }

        fun addItemDecoration(gridDividerItemDecoration: androidx.recyclerview.widget.RecyclerView.ItemDecoration): Builder<Item, Holder, T> {
            if (mViewGroup is androidx.recyclerview.widget.RecyclerView) {
                mViewGroup.addItemDecoration(gridDividerItemDecoration)
            }
            return this
        }

        fun setNestedScrollingEnabled(enabled: Boolean): Builder<Item, Holder, T> {
            if (mViewGroup is androidx.recyclerview.widget.RecyclerView) {
                mViewGroup.isNestedScrollingEnabled = enabled
            }
            return this
        }


        fun build(): UniversalBind<Item, Holder, T> {
            return UniversalBind(this)
        }
    }


}
