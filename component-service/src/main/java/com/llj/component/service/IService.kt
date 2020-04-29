package com.llj.component.service

import android.content.Context
import android.util.ArrayMap
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2020/4/29
 */
interface IService : IProvider {

    companion object {
        val sMap = ArrayMap<String, String>()
    }

    fun setParam(key: String, value: String) {
        sMap[key] = value
    }

    fun call(context: Context, action: String)
}