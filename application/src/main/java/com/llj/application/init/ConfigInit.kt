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
import com.llj.lib.utils.helper.Utils
import com.llj.lib.webview.manager.IWebViewClient
import com.llj.lib.webview.manager.WebViewConfig
import com.llj.lib.webview.manager.WebViewManager
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
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
@AppInit(priority = 2, description = "ConfigInit")
class ConfigInit : SimpleAppInit() {


  override fun onCreate() {
    Timber.tag("init").e("ConfigInit")
    //设置共用的toolbar
    val toolbarConfig = ToolbarConfig()
    toolbarConfig.leftImageRes = R.drawable.service_icon_back
    AppManager.getInstance().toolbarConfig = toolbarConfig

    //跳转配置
    val jumpConfig = JumpConfig()
    jumpConfig.nativeScheme = CJump.SCHEME
    jumpConfig.loadingPath = CRouter.APP_LOADING_ACTIVITY
    jumpConfig.loginPath = CRouter.LOGIN_LOGIN_ACTIVITY
    AppManager.getInstance().jumpConfig = jumpConfig

    //用户信息配置
    val userInfoConfig = UserInfoConfig()
    userInfoConfig.isLogin = false
    AppManager.getInstance().userInfoConfig = userInfoConfig

    //WebView配置
    val webViewConfig = WebViewConfig()
    webViewConfig.scheme = CJump.SCHEME
    webViewConfig.iWebViewClient = object : IWebViewClient {
      override fun shouldOverrideUrlLoading(webView: WebView?, s: String?): Boolean {
        if (s != null && s.startsWith(webViewConfig.scheme)) {

        }
        return false
      }
    }
    WebViewManager.getInstance().webViewConfig = webViewConfig
  }
}