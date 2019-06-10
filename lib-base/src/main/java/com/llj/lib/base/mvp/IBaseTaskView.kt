package com.llj.lib.base.mvp

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IBaseTaskView : IBaseActivityView {

    fun onDataError(tag: Int, e: Throwable, taskId: Int)
}
