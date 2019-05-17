package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IBaseTaskView6<T> : IBaseTaskView {

    fun getParams6(): HashMap<String, Any>

    fun onDataSuccess6(result: T)

}
