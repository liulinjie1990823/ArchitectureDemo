package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView3<T> : IBaseActivityView {

    fun getParams3(): HashMap<String, Any>

    fun onDataSuccess3(result: T)

    fun onDataError(tag: Int, e: Throwable)

}
