package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView4<T> : IBaseActivityView {

    fun getParams4(): HashMap<String, Any>

    fun onDataSuccess4(result: T)


}
