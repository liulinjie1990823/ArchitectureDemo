package com.llj.application.init

import com.llj.application.R
import com.llj.socialization.SocialConstants
import com.llj.socialization.init.SocialConfig
import com.llj.socialization.init.SocialManager
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import timber.log.Timber


/**
 * describe 初始化UserInfo
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 90, description = "SocialInit")
class SocialInit : SimpleAppInit() {

  override fun needAsyncInit(): Boolean {
    return true
  }

  override fun asyncOnCreate() {
    Timber.tag(TAG).e("SocialInit")
    //分享
    val config = SocialConfig.Builder(mApplication, true).qqId(mApplication.getString(R.string.qq_id))
        .wx(mApplication.getString(R.string.wx_id), mApplication.getString(R.string.wx_secret))
        .sign(mApplication.getString(R.string.sina_id), mApplication.getString(R.string.sina_url), SocialConstants.SCOPE)
        .build()
    SocialManager.init(config)
  }
}