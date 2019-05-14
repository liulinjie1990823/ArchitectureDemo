package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView5<T> : IBaseActivityView {

    fun getParams5(): HashMap<String, Any>

    fun onDataSuccess5(result: T)

}
