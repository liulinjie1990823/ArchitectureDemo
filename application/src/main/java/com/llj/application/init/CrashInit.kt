package com.llj.application.init

import com.alibaba.android.arouter.launcher.ARouter
import com.llj.application.AppApplication
import com.llj.application.R
import com.llj.component.service.arouter.CJump
import com.llj.component.service.arouter.CRouter
import com.llj.component.service.preference.UserInfoPreference
import com.llj.lib.base.AppManager
import com.llj.lib.base.config.JumpConfig
import com.llj.lib.base.config.ToolbarConfig
import com.llj.lib.base.config.UserInfoConfig
import com.llj.lib.base.help.CrashHelper
import com.llj.lib.utils.LogUtil
import com.llj.lib.webview.manager.IWebViewClient
import com.llj.lib.webview.manager.WebViewConfig
import com.llj.lib.webview.manager.WebViewManager
import com.llj.socialization.SocialConstants
import com.llj.socialization.init.SocialConfig
import com.llj.socialization.init.SocialManager
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.smtt.sdk.WebView
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
@AppInit(priority = 100, description = "CrashInit")
class CrashInit : SimpleAppInit() {

  override fun needAsyncInit(): Boolean {
    return true
  }

  override fun onCreate() {
  }

  override fun asyncOnCreate() {
    Timber.tag("init").e("CrashInit")
    //bugly的Crash记录
    CrashReport.initCrashReport(mApplication, mApplication.getString(R.string.bugly_id), false)
    CrashHelper.getInstance().init(mApplication) { LogUtil.LLJe(it) }
  }
}