package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityComposeView3<T1, T2, T3> : IBaseTaskView {

    fun getParams1(taskId: Int): HashMap<String, Any>?
    fun onDataSuccess1(result: T1,taskId: Int)

    fun getParams2(taskId: Int): HashMap<String, Any>?
    fun onDataSuccess2(result: T2,taskId: Int)

    fun getParams3(taskId: Int): HashMap<String, Any>?
    fun onDataSuccess3(result: T3,taskId: Int)

}
