package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView2<T1, T2> : IBaseActivityView {

    fun getParams1(): HashMap<String, Any>
    fun onDataSuccess1(result: T1)

    fun getParams2(): HashMap<String, Any>
    fun onDataSuccess2(result: T2)

    fun onDataError(tag: Int, e: Throwable)


}
