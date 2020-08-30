package com.llj.application.service

import android.content.Context
import android.util.ArrayMap
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * describe
 *
 * @author
 * @date
 */
interface ModuleService : IProvider {


  fun call(context: Context, event: String, param: ArrayMap<String, String>?)
}