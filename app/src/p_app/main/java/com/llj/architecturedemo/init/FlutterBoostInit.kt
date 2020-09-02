package com.llj.architecturedemo.init

import android.os.Build
import android.util.Log
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.Platform
import com.idlefish.flutterboost.Utils
import com.idlefish.flutterboost.interfaces.INativeRouter
import com.llj.architecturedemo.MainApp
import com.llj.architecturedemo.flutter.PageRouter
import com.llj.architecturedemo.flutter.TextPlatformViewFactory
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
import io.flutter.embedding.android.FlutterView
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec
import timber.log.Timber


/**
 * describe 初始化UserInfo
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 1, description = "FlutterBoostInit")
class FlutterBoostInit : SimpleAppInit() {
  override fun needAsyncInit(): Boolean {
    return true
  }

  override fun asyncOnCreate() {
    Timber.tag("init").e("FlutterBoostInit")
    initFlutterBoost()
  }

  private fun initFlutterBoost() {
    //路由,Flutter 启动Native页面的时候回调这里
    val router = INativeRouter { context, url, urlParams, requestCode, exts ->
      Log.i(MainApp.TAG, "路由--INativeRouter:url=$url")
      Log.i(MainApp.TAG, "路由--INativeRouter:requestCode=$requestCode")
      Log.i(MainApp.TAG, "路由--INativeRouter:urlParams=$urlParams")
      Log.i(MainApp.TAG, "路由--INativeRouter:exts=$exts")
      val assembleUrl: String = Utils.assembleUrl(url, urlParams)
      PageRouter.openPageByUrl(context, assembleUrl, urlParams);
    }
//        插件注册
    val boostLifecycleListener: FlutterBoost.BoostLifecycleListener = object : FlutterBoost.BoostLifecycleListener {
      override fun beforeCreateEngine() {}
      override fun onEngineCreated() {

        // 注册MethodChannel，监听flutter侧的getPlatformVersion调用
        val methodChannel = MethodChannel(FlutterBoost.instance().engineProvider().dartExecutor, "flutter_native_channel")
        methodChannel.setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
          if (call.method == "getPlatformVersion") {
            result.success(Build.VERSION.RELEASE)
          } else {
            result.notImplemented()
          }
        }

        // 注册PlatformView viewTypeId要和flutter中的viewType对应
        FlutterBoost
            .instance()
            .engineProvider()
            .platformViewsController
            .registry
            .registerViewFactory("plugins.test/view", TextPlatformViewFactory(StandardMessageCodec.INSTANCE))
      }

      override fun onPluginsRegistered() {}
      override fun onEngineDestroy() {}
    }

    //配置
    //配置
    val platform: Platform = FlutterBoost.ConfigBuilder(mApplication, router)
        .isDebug(true)
        .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
        .renderMode(FlutterView.RenderMode.texture)
        .lifecycleListener(boostLifecycleListener)
        .build()

    FlutterBoost.instance().init(platform)

  }
}