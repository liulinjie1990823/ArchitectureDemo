package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityComposeView2<T1, T2> : IBaseTaskView {

    fun getParams1(taskId: Int): HashMap<String, Any>?
    fun onDataSuccess1(result: T1,taskId: Int)

    fun getParams2(taskId: Int): HashMap<String, Any>?
    fun onDataSuccess2(result: T2,taskId: Int)

}
