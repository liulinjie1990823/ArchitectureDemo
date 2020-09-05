package com.llj.application.init

import com.llj.application.AppApplication
import com.llj.application.preference.UserInfoPreference
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import timber.log.Timber


/**
 * describe 初始化UserInfo
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 20, description = "UserInfoInit")
class UserInfoInit : SimpleAppInit() {
  override fun onCreate() {
    Timber.tag("init").e("UserInfoInit")
    AppApplication.mUserInfoVo = UserInfoPreference.getInstance().getUserInfo()
  }
}