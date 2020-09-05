package com.llj.application.init

import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import timber.log.Timber


/**
 * describe ImInit
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 80, description = "ImInit")
class ImInit : SimpleAppInit() {


  override fun onCreate() {
    Timber.tag(TAG).e("ImInit")
  }
}