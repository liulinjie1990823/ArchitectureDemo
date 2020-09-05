package com.llj.application.init

import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import timber.log.Timber


/**
 * describe 初始化UserInfo
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 10, description = "LogInit")
class LogInit : SimpleAppInit() {

  override fun onCreate() {
    //设置日志
    Timber.plant(object : Timber.DebugTree() {
      override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (mIsDebug) {
          super.log(priority, tag, message, t)
        }
      }
    })
    Timber.tag("init").e("LogInit")
  }
}