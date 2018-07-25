package com.llj.adapter

import android.support.v7.widget.*
import android.view.ViewGroup
import com.llj.adapter.converter.UniversalConverterFactory

/**
 * MerchantCenter
 * describe:
 * author liulj
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


        fun setLayoutManager(layoutManager: RecyclerView.LayoutManager): Builder<Item, Holder, T> {
            if (mViewGroup is RecyclerView) {
                mViewGroup.layoutManager = layoutManager
            }
            return this
        }

        fun setLinearLayoutManager(): Builder<Item, Holder, T> {
            if (mViewGroup is RecyclerView) {
                mViewGroup.layoutManager = LinearLayoutManager(null)
            }
            return this
        }

        fun setLinearLayoutManager(orientation: Int): Builder<Item, Holder, T> {
            if (mViewGroup is RecyclerView) {
                mViewGroup.layoutManager = LinearLayoutManager(null, orientation, false)
            }
            return this
        }

        fun setGridLayoutManager(spanCount: Int): Builder<Item, Holder, T> {
            if (mViewGroup is RecyclerView) {
                mViewGroup.layoutManager = GridLayoutManager(null, spanCount)
            }
            return this
        }

        fun setStaggeredGridLayoutManager(spanCount: Int, orientation: Int): Builder<Item, Holder, T> {
            if (mViewGroup is RecyclerView) {
                mViewGroup.layoutManager = StaggeredGridLayoutManager(spanCount, orientation)
            }
            return this
        }

        fun addItemDecoration(dividerItemDecoration: DividerItemDecoration): Builder<Item, Holder, T> {
            if (mViewGroup is RecyclerView) {
                mViewGroup.addItemDecoration(dividerItemDecoration)
            }
            return this
        }

        fun addItemDecoration(gridDividerItemDecoration: RecyclerView.ItemDecoration): Builder<Item, Holder, T> {
            if (mViewGroup is RecyclerView) {
                mViewGroup.addItemDecoration(gridDividerItemDecoration)
            }
            return this
        }

        fun setNestedScrollingEnabled(enabled: Boolean): Builder<Item, Holder, T> {
            if (mViewGroup is RecyclerView) {
                mViewGroup.isNestedScrollingEnabled = enabled
            }
            return this
        }


        fun build(): UniversalBind<Item, Holder, T> {
            return UniversalBind(this)
        }
    }


}
