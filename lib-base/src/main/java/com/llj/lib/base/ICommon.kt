package com.llj.lib.base

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View

/**
 * ArchitectureDemo
 * describe:通用的方法
 * @author llj
 * @date 2018/5/24
 */
interface ICommon {
    fun getIntentData(intent: Intent) {}

    fun getArgumentsData(bundle: Bundle) {}

    fun layoutView(): View? {
        return null
    }

    @LayoutRes
    fun layoutId(): Int

    fun initViews(savedInstanceState: Bundle?)

    fun initData()
}
