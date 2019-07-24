package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IDeleteTaskView<T> : IBaseTaskView {

    fun getDeleteParams(taskId: Int): HashMap<String, Any>?

    fun onDeleteSuccess(result: T, taskId: Int)


}
