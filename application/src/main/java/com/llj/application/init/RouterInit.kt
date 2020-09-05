package com.llj.application.init

import com.alibaba.android.arouter.launcher.ARouter
import com.llj.lib.jump.api.JumpHelp
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import timber.log.Timber


/**
 * describe 初始化路由
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 14, description = "初始化路由")
class RouterInit : SimpleAppInit() {

  override fun needAsyncInit(): Boolean {
    return true
  }

  override fun asyncOnCreate() {
    Timber.tag(TAG).e("RouterInit")
    //页面跳转
    JumpHelp.init(mApplication)

  }

  override fun onCreate() {
    if (mIsDebug) {   // These two lines must be written before init, otherwise these configurations will be invalid in the init process
      ARouter.openLog()     // Print log
      ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
    }
    ARouter.init(mApplication)
  }
}