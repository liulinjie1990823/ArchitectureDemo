package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView8<T> : IBaseActivityView {

    fun getParams8(): HashMap<String, Any>

    fun onDataSuccess8(result: T)

}
