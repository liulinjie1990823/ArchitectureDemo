package com.llj.lib.base.mvp

import java.util.*

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
interface IBaseActivityComposeView4<T1, T2, T3, T4> : IBaseTaskView {

    fun getParams1(taskId: Int): HashMap<String, Any>?
    fun onDataSuccess1(result: T1, taskId: Int)

    fun getParams2(taskId: Int): HashMap<String, Any>?
    fun onDataSuccess2(result: T2, taskId: Int)

    fun getParams3(taskId: Int): HashMap<String, Any>?
    fun onDataSuccess3(result: T3, taskId: Int)

    fun getParams4(taskId: Int): HashMap<String, Any>?
    fun onDataSuccess4(result: T4, taskId: Int)

}
