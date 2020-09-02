package com.llj.component.service.init

import com.alibaba.android.arouter.launcher.ARouter
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import skin.support.SkinCompatManager
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater
import timber.log.Timber


/**
 * describe 初始化换肤
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 40, description = "SkinInit")
class SkinInit : SimpleAppInit() {

  override fun needAsyncInit(): Boolean {
    return true
  }

  override fun asyncOnCreate() {
    Timber.tag("init").e("SkinInit")

    SkinCompatManager.withoutActivity(mApplication)                         // 基础控件换肤初始化
        .addInflater(SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
        .addInflater(SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
        .addInflater(SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
        .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
        .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
        .loadSkin()
  }

  override fun onCreate() {


  }
}