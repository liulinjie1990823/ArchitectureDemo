package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView6<T> : IBaseActivityView {

    fun getParams6(): HashMap<String, Any>

    fun onDataSuccess6(result: T)

}
