package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IPostLikeView<T> : IBaseTaskView {

    fun getPostLikeParams(taskId: Int): HashMap<String, Any>?

    fun onPostLikeSuccess(result: T, taskId: Int)


}
