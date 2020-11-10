package com.llj.component.service

import android.content.pm.ApplicationInfo
import androidx.annotation.CallSuper
import com.llj.lib.base.BaseApplication


/**
 * ArchitectureDemo
 * describe:所有app需要继承的Application，初始化一些公用的东西
 * @author llj
 * @date 2018/7/3
 */
abstract class PlatformApplication : BaseApplication() {

  @CallSuper
  override fun onCreate() {
    super.onCreate()
  }

  override fun isDebug(): Boolean {
    return applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
  }

}
