package com.llj.application.init

import com.llj.lib.tracker.Tracker
import com.llj.lib.tracker.TrackerConfig
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import timber.log.Timber


/**
 * describe 初始化UserInfo
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 70, description = "TrackerInit")
class TrackerInit : SimpleAppInit() {
  override fun needAsyncInit(): Boolean {
    return false
  }

  override fun onCreate() {
    super.onCreate()

    Timber.tag(TAG).e("TrackerInit")
    //设置埋点
    Tracker.init(mApplication, TrackerConfig.Builder().build())
  }

  override fun asyncOnCreate() {

  }
}