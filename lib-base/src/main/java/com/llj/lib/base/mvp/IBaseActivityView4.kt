package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView4<T1, T2, T3, T4> : IBaseActivityView {

    fun getParams1(): HashMap<String, Any>
    fun onDataSuccess1(result: T1)

    fun getParams2(): HashMap<String, Any>
    fun onDataSuccess2(result: T2)

    fun getParams3(): HashMap<String, Any>
    fun onDataSuccess3(result: T3)

    fun getParams4(): HashMap<String, Any>
    fun onDataSuccess4(result: T4)

    fun onDataError(tag: Int, e: Throwable)
}
