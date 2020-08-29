package com.llj.architecturedemo

import android.os.Build
import android.util.Log
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoost.BoostLifecycleListener
import com.idlefish.flutterboost.Platform
import com.idlefish.flutterboost.Utils
import com.idlefish.flutterboost.interfaces.INativeRouter
import com.llj.application.AppApplication
import com.llj.architecturedemo.di.DaggerMainComponent
import com.llj.architecturedemo.di.MainComponent
import com.llj.architecturedemo.flutter.PageRouter
import com.llj.architecturedemo.flutter.TextPlatformViewFactory
import io.flutter.embedding.android.FlutterView
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec


/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2020/4/29
 */
class MainApp : AppApplication() {

    companion object{
        var TAG="MainApp"
    }

    private lateinit var mMainComponent: MainComponent
    override fun onCreate() {
        super.onCreate()

        mMainComponent = DaggerMainComponent.builder()
                .appComponent(mAppComponent)
                .build()

        //路由,Flutter 启动Native页面的时候回调这里
        val router = INativeRouter { context, url, urlParams, requestCode, exts ->
            Log.i(TAG, "路由--INativeRouter:url=$url")
            Log.i(TAG, "路由--INativeRouter:requestCode=$requestCode")
            Log.i(TAG, "路由--INativeRouter:urlParams=$urlParams")
            Log.i(TAG, "路由--INativeRouter:exts=$exts")
            val assembleUrl: String = Utils.assembleUrl(url, urlParams)
            PageRouter.openPageByUrl(context,assembleUrl, urlParams);
        }
//        插件注册
        val boostLifecycleListener: BoostLifecycleListener = object : BoostLifecycleListener {
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
        val platform: Platform = FlutterBoost.ConfigBuilder(this, router)
            .isDebug(true)
            .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
            .renderMode(FlutterView.RenderMode.texture)
            .lifecycleListener(boostLifecycleListener)
            .build()

        FlutterBoost.instance().init(platform)
    }

}