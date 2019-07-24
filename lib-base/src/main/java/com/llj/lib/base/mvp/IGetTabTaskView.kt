package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IGetTabTaskView<T> : IBaseTaskView {

    fun getGetTabParams(taskId: Int): HashMap<String, Any>?

    fun onGetTabSuccess(result: T, taskId: Int)


}
