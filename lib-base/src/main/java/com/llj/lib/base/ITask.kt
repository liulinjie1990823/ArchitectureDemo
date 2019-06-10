package com.llj.lib.base

import io.reactivex.disposables.Disposable

/**
 * ArchitectureDemo
 * describe: 任务控制
 * 任务的添加和移除,在基类中需要一个ArrayMap来控制task
 * @author llj
 * @date 2018/7/5
 */
interface ITask {

    /**
     * 添加任务
     */
    fun addDisposable(taskId: Int, disposable: Disposable)

    /**
     * 移除对应的任务
     */
    fun removeDisposable(taskId: Int?)

    /**
     * 移除所有的任务
     */
    fun removeAllDisposable()

}
