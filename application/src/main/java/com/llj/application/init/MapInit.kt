package com.llj.application.init

import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import timber.log.Timber


/**
 * describe MapInit
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 101, description = "MapInit")
class MapInit : SimpleAppInit() {


  override fun onCreate() {
    Timber.tag("init").e("MapInit")
  }
}