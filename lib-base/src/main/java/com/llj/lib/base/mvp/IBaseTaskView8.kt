package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IBaseTaskView8<T> : IBaseTaskView {

    fun getParams8(): HashMap<String, Any>

    fun onDataSuccess8(result: T)

}
