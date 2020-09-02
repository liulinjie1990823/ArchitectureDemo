package com.llj.application.init

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoraemonKit
import com.llj.application.AppApplication
import com.llj.application.R
import com.llj.application.utils.BuildTypeUtil
import com.llj.component.service.arouter.CJump
import com.llj.component.service.arouter.CRouter
import com.llj.component.service.preference.UserInfoPreference
import com.llj.lib.base.AppManager
import com.llj.lib.base.config.JumpConfig
import com.llj.lib.base.config.ToolbarConfig
import com.llj.lib.base.config.UserInfoConfig
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.base.help.FilePathHelper
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter
import com.llj.lib.jump.api.JumpHelp
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat
import com.llj.lib.utils.AToastUtils
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
@AppInit(priority = 3, description = "CustomInit")
class CustomInit : SimpleAppInit() {
  override fun needAsyncInit(): Boolean {
    return true
  }

  override fun onCreate() {
    Timber.tag("init").e("CustomInit StatusBar")

    //设置状态栏监听
    mApplication.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
      override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        val simpleName = activity.javaClass.simpleName
        if ("MainActivity" == simpleName) {
          //status和界面中的布局覆盖布局，界面中加了fitWindow,有padding效果，覆盖白字
          StatusBarCompat.translucentStatusBar(activity.window, true)
          LightStatusBarCompat.setLightStatusBar(activity.window, false)
        } else if (simpleName != "KeyboardStateActivity") {
          //status和界面中的布局线性布局，白低黑字
          StatusBarCompat.translucentStatusBar(activity.window, true)
          LightStatusBarCompat.setLightStatusBar(activity.window, true)

        }
      }
    })

  }

  override fun asyncOnCreate() {
    super.asyncOnCreate()
    AToastUtils.init()
    DisplayHelper.init(mApplication)
    FilePathHelper.init(mApplication)

    Timber.tag("init").e("CustomInit JumpHelp")
    //页面跳转
    JumpHelp.init(mApplication)
  }
}