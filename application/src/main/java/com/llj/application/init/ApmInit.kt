package com.llj.application.init

import com.didichuxing.doraemonkit.DoraemonKit
import com.llj.application.utils.BuildTypeUtil
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import timber.log.Timber


/**
 * describe 初始化UserInfo
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 200, onlyForDebug = true, description = "ApmInit")
class ApmInit : SimpleAppInit() {

  override fun needAsyncInit(): Boolean {
    return true
  }

  override fun asyncOnCreate() {
    Timber.tag(TAG).e("ApmInit")

    DoraemonKit.install(mApplication)
    BuildTypeUtil.initFlipper(mApplication)
  }


}