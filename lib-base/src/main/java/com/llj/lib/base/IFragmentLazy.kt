package com.llj.lib.base

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
interface IFragmentLazy {

    fun useLazyLoad(): Boolean {
        return false
    }

    fun hasInitAndVisible(): Boolean

    fun onLazyLoad() {

    }
}
