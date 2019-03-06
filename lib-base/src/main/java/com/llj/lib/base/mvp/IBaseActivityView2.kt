package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView2<T> : IBaseActivityView {

    fun getParams2(): HashMap<String, Any>

    fun onDataSuccess2(result: T)

    fun onDataError(tag: Int, e: Throwable)

}
