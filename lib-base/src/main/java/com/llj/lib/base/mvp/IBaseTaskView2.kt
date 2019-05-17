package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IBaseTaskView2<T> : IBaseTaskView {

    fun getParams2(): HashMap<String, Any>

    fun onDataSuccess2(result: T)


}
