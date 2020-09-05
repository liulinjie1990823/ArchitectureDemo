package com.llj.application.init

import android.app.Application
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher
import com.llj.application.AppApplication
import com.llj.lib.image.loader.ImageLoader
import com.llj.lib.image.loader.engine.fresco.FrescoEngine
import com.llj.lib.image.loader.engine.fresco.FrescoUtils
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import timber.log.Timber


/**
 * describe ImageLoadInit
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 13, description = "ImageLoadInit")
class ImageLoadInit : SimpleAppInit() {

  override fun needAsyncInit(): Boolean {
    return false
  }

  override fun asyncOnCreate() {

  }

  override fun onCreate() {
    init(mApplication)
  }

  private fun init(application: Application) {
    Timber.tag(TAG).e("ImageLoadInit")

    FrescoUtils.initFresco(application, OkHttpNetworkFetcher((application as AppApplication).mAppComponent.okHttpClient()))
    //图片加载引擎
    ImageLoader.addImageLoadEngine(0, FrescoEngine())
  }

}