package com.llj.application.init

import android.app.Activity
import android.os.Bundle
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.base.help.FilePathHelper
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat
import com.llj.lib.utils.AToastUtils
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
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

  }
}