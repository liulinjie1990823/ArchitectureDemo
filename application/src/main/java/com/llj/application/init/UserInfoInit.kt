package com.llj.application.init

import com.alibaba.android.arouter.launcher.ARouter
import com.llj.application.AppApplication
import com.llj.component.service.preference.UserInfoPreference
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import skin.support.SkinCompatManager
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater
import timber.log.Timber


/**
 * describe 初始化UserInfo
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 4, description = "UserInfoInit")
class UserInfoInit : SimpleAppInit() {
  override fun onCreate() {
    Timber.tag("init").e("UserInfoInit")
    AppApplication.mUserInfoVo = UserInfoPreference.getInstance().getUserInfo()
  }
}