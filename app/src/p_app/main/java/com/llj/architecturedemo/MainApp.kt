package com.llj.architecturedemo

import android.content.Intent
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
import io.flutter.embedding.android.FlutterView
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec
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
    mStartTime = System.currentTimeMillis()

    super.onCreate()

    mMainComponent = DaggerMainComponent.builder()
        .appComponent(mAppComponent)
        .build()

    initFlutterBoost()
    initMatrix()
    val diff = System.currentTimeMillis() - mStartTime


    Timber.tag(TAG).e("MainApp start up %d ms", diff)

  }

  private fun initFlutterBoost() {
    //路由,Flutter 启动Native页面的时候回调这里
    val router = INativeRouter { context, url, urlParams, requestCode, exts ->
      Log.i(TAG, "路由--INativeRouter:url=$url")
      Log.i(TAG, "路由--INativeRouter:requestCode=$requestCode")
      Log.i(TAG, "路由--INativeRouter:urlParams=$urlParams")
      Log.i(TAG, "路由--INativeRouter:exts=$exts")
      val assembleUrl: String = Utils.assembleUrl(url, urlParams)
      PageRouter.openPageByUrl(context, assembleUrl, urlParams);
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

  private fun initMatrix() {
    val dynamicConfig = DynamicConfigImplDemo()
    val matrixEnable: Boolean = dynamicConfig.isMatrixEnable
    val fpsEnable: Boolean = dynamicConfig.isFpsEnable
    val traceEnable: Boolean = dynamicConfig.isTraceEnable

    MatrixLog.i(TAG, "MatrixApplication.onCreate")

    val builder: Matrix.Builder = Matrix.Builder(this)
    builder.patchListener(TestPluginListener(this))

    //trace
    val traceConfig: TraceConfig = TraceConfig.Builder()
        .dynamicConfig(dynamicConfig)
        .enableFPS(fpsEnable)
        .enableEvilMethodTrace(traceEnable)
        .enableAnrTrace(traceEnable)
        .enableStartup(traceEnable)
        .splashActivities("com.llj.loading.ui.activity.LoadingActivity;")
        .isDebug(true)
        .isDevEnv(false)
        .build()

    val tracePlugin = TracePlugin(traceConfig)
    builder.plugin(tracePlugin)

    if (matrixEnable) {

      //resource
      val intent = Intent()
      val mode: ResourceConfig.DumpMode = ResourceConfig.DumpMode.AUTO_DUMP
      MatrixLog.i(TAG, "Dump Activity Leak Mode=%s", mode)
      intent.setClassName(this.packageName, "com.tencent.mm.ui.matrix.ManualDumpActivity")
      val resourceConfig: ResourceConfig = ResourceConfig.Builder()
          .dynamicConfig(dynamicConfig)
          .setAutoDumpHprofMode(mode) //                .setDetectDebuger(true) //matrix test code
          .setNotificationContentIntent(intent)
          .build()
      builder.plugin(ResourcePlugin(resourceConfig))
      ResourcePlugin.activityLeakFixer(this)

      //io
      val ioCanaryPlugin = IOCanaryPlugin(IOConfig.Builder()
          .dynamicConfig(dynamicConfig)
          .build())
      builder.plugin(ioCanaryPlugin)


      // prevent api 19 UnsatisfiedLinkError
      //sqlite
      var sqlLiteConfig: SQLiteLintConfig?
      try {
        sqlLiteConfig = SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY)
      } catch (t: Throwable) {
        sqlLiteConfig = SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY)
      }
      builder.plugin(SQLiteLintPlugin(sqlLiteConfig))
    }

    Matrix.init(builder.build())

    //start only startup tracer, close other tracer.

    //start only startup tracer, close other tracer.
    tracePlugin.start()

  }

}