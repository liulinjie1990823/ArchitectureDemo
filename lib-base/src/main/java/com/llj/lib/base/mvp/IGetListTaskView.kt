package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IGetListTaskView<T> : IBaseTaskView {

    fun getGetListParams(taskId: Int): HashMap<String, Any>?

    fun onGetListSuccess(result: T, taskId: Int)


}
