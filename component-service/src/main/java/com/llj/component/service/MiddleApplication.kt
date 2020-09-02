package com.llj.component.service

import android.content.pm.ApplicationInfo
import androidx.annotation.CallSuper
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher
import com.llj.component.service.preference.UserInfoPreference
import com.llj.component.service.utils.BuildTypeUtil
import com.llj.component.service.vo.UserInfoVo
import com.llj.lib.base.BaseApplication
import com.llj.lib.image.loader.engine.fresco.FrescoUtils
import skin.support.SkinCompatManager
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater


/**
 * ArchitectureDemo
 * describe:所有app需要继承的Application，初始化一些公用的东西
 * @author llj
 * @date 2018/7/3
 */
abstract class MiddleApplication : BaseApplication() {

  @CallSuper
  override fun onCreate() {
    super.onCreate()
  }

  override fun isDebug(): Boolean {
    return applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
  }

}
