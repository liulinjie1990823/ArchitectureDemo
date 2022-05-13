package com.llj.adapter.refresh

import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalAdapter
import com.llj.adapter.XViewHolder
import com.llj.adapter.model.TypeItem
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/20
 */
class RefreshHelper2<Item : TypeItem, Holder : XViewHolder>(
  private val pageSize: Int,
  private var mSmartRefreshLayout: SmartRefreshLayout?,
  private var mAdapter: ListBasedAdapter<Item?, Holder>?
) : IRefresh2<Item?, Holder> {

  private var mPagerHelper: PagerHelper = PagerHelper(pageSize)

  //如果需要在getIntentData中请求数据，需要RefreshHelper提前初始化，之后再通过setRefreshLayout设置SmartRefreshLayout和ListBasedAdapter
  constructor(pageSize: Int)
      : this(pageSize, null, null)


  fun setRefreshLayout(mSmartRefreshLayout: SmartRefreshLayout?, mAdapter: ListBasedAdapter<Item?, Holder>?) {
    this.mSmartRefreshLayout = mSmartRefreshLayout
    this.mAdapter = mAdapter
  }

  override fun getAdapter(): UniversalAdapter<Item?, Holder>? {
    return mAdapter
  }

  override fun size(): Int {
    return mAdapter?.getCount() ?: 0
  }

  override fun finishRefreshOrLoadMore(success: Boolean) {
    finishRefreshOrLoadMore(success, false)
  }

  override fun finishRefreshOrLoadMore(success: Boolean, hasNextPage: Boolean) {
    if (isFirstPage()) {
      mSmartRefreshLayout?.finishRefresh(0, success, hasNextPage)
    } else {
      mSmartRefreshLayout?.finishLoadMore(0, success, hasNextPage)
    }
  }

  override fun handleData(hasNextPage: Boolean, list: Collection<Item?>?) {
    handleData(true, hasNextPage, list)
  }

  override fun handleData(shouldSetEnableLoadMore: Boolean, hasNextPage: Boolean, list: Collection<Item?>?) {

  }

  private fun <T> isEmpty(list: Collection<T>?): Boolean {
    return list == null || list.isEmpty()
  }

  //刷新完data不为null调用
  private fun checkHasMoreData(shouldSetEnableLoadMore: Boolean, hasNextPage: Boolean) {
    mPagerHelper.addPageNum(hasNextPage)
    //在finishRefreshOrLoadMore(success: Boolean, hasNextPage: Boolean)中已经设置了是否有更多数据
//    mSmartRefreshLayout.setNoMoreData(!hasNextPage)
    if (shouldSetEnableLoadMore)
      mSmartRefreshLayout?.setEnableLoadMore(hasNextPage)
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