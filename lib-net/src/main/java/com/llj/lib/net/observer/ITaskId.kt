package com.llj.lib.net.observer

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/11
 */
interface ITaskId {
    fun getRequestId(): Int

    fun setRequestId(taskId: Int)
}
