package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView9<T> : IBaseActivityView {

    fun getParams9(): HashMap<String, Any>

    fun onDataSuccess9(result: T)

}
