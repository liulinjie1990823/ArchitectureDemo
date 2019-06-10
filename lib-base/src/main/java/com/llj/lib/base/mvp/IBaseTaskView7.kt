package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IBaseTaskView7<T> : IBaseTaskView {

    fun getParams7(taskId: Int): HashMap<String, Any>?

    fun onDataSuccess7(result: T, taskId: Int)

}
