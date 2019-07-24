package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IGetBannerTaskView<T> : IBaseTaskView {

    fun getGetBannerParams(taskId: Int): HashMap<String, Any>?

    fun onGetBannerSuccess(result: T, taskId: Int)


}
