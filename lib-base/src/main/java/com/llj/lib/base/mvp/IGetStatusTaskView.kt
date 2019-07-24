package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IGetStatusTaskView<T> : IBaseTaskView {

    fun getGetStatusParams(taskId: Int): HashMap<String, Any>?

    fun onGetStatusSuccess(result: T, taskId: Int)


}
