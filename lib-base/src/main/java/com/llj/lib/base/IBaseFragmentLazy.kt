package com.llj.lib.base

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/24
 */
interface IBaseFragmentLazy {

    fun useLazyLoad(): Boolean {
        return false
    }

    fun hasInitAndVisible(): Boolean

    fun onLazyInitData() {

    }
}
