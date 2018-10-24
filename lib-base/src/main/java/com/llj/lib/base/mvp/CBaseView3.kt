package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface CBaseView3<T1, T2, T3> : IView {

    fun getParams1(): HashMap<String, Any>
    fun getParams2(): HashMap<String, Any>
    fun getParams3(): HashMap<String, Any>

    fun onDataSuccess1(result: T1)

    fun onDataSuccess2(result: T2)

    fun onDataSuccess3(result: T3)

    fun onDataError(tag: Int, e: Throwable)
}
