package com.llj.adapter.refresh

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/20
 */
interface IPager {

    fun getInitPageNum(): Int

    fun getCurrentPageNum(): Int

    fun getPageSize(): Int

    fun isFirstPage(): Boolean

    fun addPageNum()

    fun addPageNum(dataSize: Int)

    fun addPageNum(hasNextPage: Boolean)

    fun resetPageNum()
}
