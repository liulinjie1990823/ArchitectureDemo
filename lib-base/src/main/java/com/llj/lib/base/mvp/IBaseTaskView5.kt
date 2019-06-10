package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IBaseTaskView5<T> : IBaseTaskView {

    fun getParams5(taskId: Int): HashMap<String, Any>?

    fun onDataSuccess5(result: T, taskId: Int)

}
