package com.llj.application.init

import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoraemonKit
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher
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
import com.llj.lib.image.loader.ImageLoader
import com.llj.lib.image.loader.engine.fresco.FrescoEngine
import com.llj.lib.image.loader.engine.fresco.FrescoUtils
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
 * describe
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 1, description = "ImageLoadInit")
class ImageLoadInit : SimpleAppInit() {

  override fun needAsyncInit(): Boolean {
    return true
  }

  override fun asyncOnCreate() {
    Timber.tag("init").e("ImageLoadInit")

    FrescoUtils.initFresco(mApplication, OkHttpNetworkFetcher((mApplication as AppApplication).mAppComponent.okHttpClient()))
    //图片加载引擎
    ImageLoader.addImageLoadEngine(0, FrescoEngine())

  }

  override fun onCreate() {
  }
}