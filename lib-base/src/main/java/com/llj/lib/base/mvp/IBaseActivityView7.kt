package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityView7<T> : IBaseActivityView {

    fun getParams7(): HashMap<String, Any>

    fun onDataSuccess7(result: T)

}
