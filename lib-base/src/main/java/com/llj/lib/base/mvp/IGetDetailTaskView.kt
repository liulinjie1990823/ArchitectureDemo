package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IGetDetailTaskView<T> : IBaseTaskView {

    fun getGetDetailParams(taskId: Int): HashMap<String, Any>?

    fun onGetDetailSuccess(result: T, taskId: Int)

}
