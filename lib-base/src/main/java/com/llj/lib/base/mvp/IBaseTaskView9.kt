package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IBaseTaskView9<T> : IBaseTaskView {

    fun getParams9(taskId: Int): HashMap<String, Any>?

    fun onDataSuccess9(result: T, taskId: Int)

}
