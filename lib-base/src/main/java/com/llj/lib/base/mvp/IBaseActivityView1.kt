package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView1<T> : IBaseActivityView {

    fun getParams1(): HashMap<String, Any>

    fun onDataSuccess1(result: T)


}
