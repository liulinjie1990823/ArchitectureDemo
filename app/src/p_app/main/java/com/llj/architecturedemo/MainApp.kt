package com.llj.architecturedemo

import android.app.Application
import android.content.Intent
import android.content.res.Configuration
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
import com.llj.architecturedemo.matrix.DynamicConfigImplDemo
import com.llj.architecturedemo.matrix.TestPluginListener
import com.sankuai.erp.component.appinit.api.AppInitManager
import com.sankuai.erp.component.appinit.common.AppInitCallback
import com.sankuai.erp.component.appinit.common.AppInitItem
import com.sankuai.erp.component.appinit.common.ChildInitTable
import com.tencent.matrix.Matrix
import com.tencent.matrix.iocanary.IOCanaryPlugin
import com.tencent.matrix.iocanary.config.IOConfig
import com.tencent.matrix.resource.ResourcePlugin
import com.tencent.matrix.resource.config.ResourceConfig
import com.tencent.matrix.trace.TracePlugin
import com.tencent.matrix.trace.config.TraceConfig
import com.tencent.matrix.util.MatrixLog
import com.tencent.sqlitelint.SQLiteLint
import com.tencent.sqlitelint.SQLiteLintPlugin
import com.tencent.sqlitelint.config.SQLiteLintConfig
import io.flutter.embedding.android.FlutterView
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec
import timber.log.Timber

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2020/4/29
 */
class MainApp : AppApplication() {

  companion object {
    var TAG = "MainApp"
  }

  private lateinit var mMainComponent: MainComponent

  private var mStartTime: Long = 0

  override fun onCreate() {
    Log.e("AppInit", "MainApp start")
    mStartTime = System.currentTimeMillis()
    super.onCreate()
//    mMainComponent = DaggerMainComponent.builder()
//        .appComponent(mAppComponent)
//        .build()

    val diff = System.currentTimeMillis() - mStartTime
    Timber.tag("AppInit").e("MainApp start up %d ms", diff)
  }


}