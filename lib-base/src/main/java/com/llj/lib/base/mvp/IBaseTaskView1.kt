package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IBaseTaskView1<T> : IBaseTaskView {

    fun getParams1(): HashMap<String, Any>

    fun onDataSuccess1(result: T)


}
