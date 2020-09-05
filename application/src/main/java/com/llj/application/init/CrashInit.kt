package com.llj.application.init

import com.llj.application.R
import com.llj.lib.base.help.CrashHelper
import com.llj.lib.utils.LogUtil
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import com.tencent.bugly.crashreport.CrashReport
import timber.log.Timber


/**
 * describe CrashInit
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 12, description = "CrashInit")
class CrashInit : SimpleAppInit() {

  override fun needAsyncInit(): Boolean {
    return true
  }

  override fun onCreate() {
  }

  override fun asyncOnCreate() {
    Timber.tag(TAG).e("CrashInit")
    //bugly的Crash记录
    CrashReport.initCrashReport(mApplication, mApplication.getString(R.string.bugly_id), false)
    CrashHelper.getInstance().init(mApplication) { LogUtil.LLJe(it) }
  }
}