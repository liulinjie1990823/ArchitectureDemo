package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IBaseTaskView3<T> : IBaseTaskView {

    fun getParams3(): HashMap<String, Any>

    fun onDataSuccess3(result: T)


}
