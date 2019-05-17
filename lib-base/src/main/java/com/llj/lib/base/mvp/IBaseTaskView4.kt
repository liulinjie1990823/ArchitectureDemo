package com.llj.lib.base.mvp

import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
interface IBaseTaskView4<T> : IBaseTaskView {

    fun getParams4(): HashMap<String, Any>

    fun onDataSuccess4(result: T)


}
