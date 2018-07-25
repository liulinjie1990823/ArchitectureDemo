package com.llj.adapter.refresh

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/20
 */
class PagerHelper : IPager {

    companion object {
        var INIT_PAGE_NUM = 1
    }

    private var mPageSize = 20 //一页的大小
    private var mCurrentPageNum = 1 //当前的页数

    constructor() {}
    constructor(pageSize: Int) {
        mPageSize = pageSize
    }

    constructor(pageSize: Int, currentPageNum: Int) {
        mPageSize = pageSize
        mCurrentPageNum = currentPageNum
    }

    override fun addPageNum() {
        mCurrentPageNum++
    }

    override fun addPageNum(dataSize: Int) {
        if (mPageSize <= dataSize) {
            mCurrentPageNum++
        }
    }

    override fun addPageNum(hasNextPage: Boolean) {
        if (hasNextPage) {
            mCurrentPageNum++
        }
    }

    override fun getInitPageNum(): Int {
        return INIT_PAGE_NUM
    }

    override fun getCurrentPageNum(): Int {
        return mCurrentPageNum
    }

    override fun getPageSize(): Int {
        return mPageSize
    }

    override fun isFirstPage(): Boolean {
        return mCurrentPageNum == INIT_PAGE_NUM
    }

    override fun resetPageNum() {
        mCurrentPageNum = INIT_PAGE_NUM
    }


}
