package com.llj.lib.net.observer

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/11
 */
interface ITag {
    fun getRequestTag(): Any

    fun setRequestTag(tag: Any)
}
