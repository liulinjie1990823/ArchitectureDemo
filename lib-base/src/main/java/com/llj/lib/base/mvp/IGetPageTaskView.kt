package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IGetPageTaskView<T> : IBaseTaskView {

    fun getGetPageParams(taskId: Int): HashMap<String, Any>?

    fun onGetPageSuccess(result: T, taskId: Int)


}
