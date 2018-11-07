package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseServiceView1<T> : IBaseServiceView {

    fun getParams(): HashMap<String, Any>

    fun onDataSuccess(result: T)

    fun onDataError(e: Throwable)

}
