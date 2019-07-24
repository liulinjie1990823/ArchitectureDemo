package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IDeleteListTaskView<T> : IBaseTaskView {

    fun getDeleteListParams(taskId: Int): HashMap<String, Any>?

    fun onDeleteListSuccess(result: T, taskId: Int)


}
