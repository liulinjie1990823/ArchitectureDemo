package com.llj.adapter.refresh

import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.ViewHolder
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/20
 */
class RefreshHelper<Item, Holder : ViewHolder> : IRefresh<Item, Holder> {

    private lateinit var mPagerHelper: PagerHelper

    private var mSmartRefreshLayout: SmartRefreshLayout
    private var mAdapter: ListBasedAdapter<Item, Holder>? = null

    constructor(mSmartRefreshLayout: SmartRefreshLayout) {
        this.mSmartRefreshLayout = mSmartRefreshLayout
    }

    constructor(mSmartRefreshLayout: SmartRefreshLayout, mAdapter: ListBasedAdapter<Item, Holder>) {
        this.mSmartRefreshLayout = mSmartRefreshLayout
        this.mAdapter = mAdapter
        mPagerHelper = PagerHelper()
    }

    constructor(pageSize: Int, mSmartRefreshLayout: SmartRefreshLayout, mAdapter: ListBasedAdapter<Item, Holder>) {
        this.mSmartRefreshLayout = mSmartRefreshLayout
        this.mAdapter = mAdapter
        mPagerHelper = PagerHelper(pageSize)
    }

    override fun getAdapter(): ListBasedAdapter<Item, Holder>? {
        return mAdapter
    }

    override fun size(): Int {
        return mAdapter?.size ?: 0
    }

    override fun finishRefreshOrLoadMore(success: Boolean) {
        if (isFirstPage()) {
            mSmartRefreshLayout.finishRefresh()
            mAdapter?.clear()
        } else {
            mSmartRefreshLayout.finishLoadMore(success)
        }
    }

    //
    override fun handleData(hasNextPage: Boolean, list: Collection<Item>) {
        checkHasMoreData(hasNextPage)
        if (!isEmpty(list)) {
            mAdapter?.addAll(list)
        }
    }

    private fun <T> isEmpty(list: Collection<T>?): Boolean {
        return list == null || list.isEmpty()
    }

    //刷新完data不为null调用
    private fun checkHasMoreData(hasNextPage: Boolean) {
        mPagerHelper.addPageNum(hasNextPage)
        mSmartRefreshLayout.setNoMoreData(!hasNextPage)
    }

    override fun getInitPageNum(): Int {
        return mPagerHelper.getInitPageNum()
    }

    override fun getCurrentPageNum(): Int {
        return mPagerHelper.getCurrentPageNum()
    }

    override fun getPageSize(): Int {
        return mPagerHelper.getPageSize()
    }

    override fun isFirstPage(): Boolean {
        return mPagerHelper.isFirstPage()
    }

    override fun addPageNum() {
        mPagerHelper.addPageNum()
    }

    override fun addPageNum(dataSize: Int) {
        mPagerHelper.addPageNum(dataSize)
    }

    override fun addPageNum(hasNextPage: Boolean) {
        mPagerHelper.addPageNum(hasNextPage)
    }

    override fun resetPageNum() {
        mPagerHelper.resetPageNum()
    }

}