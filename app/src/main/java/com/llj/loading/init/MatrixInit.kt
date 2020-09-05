package com.llj.loading.init

import android.content.Intent
import com.llj.loading.init.matrix.DynamicConfigImplDemo
import com.llj.loading.init.matrix.TestPluginListener
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit
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
 * describe 初始化UserInfo
 *
 * @author liulinjie
 * @date 2020/9/1 5:15 PM
 */
@AppInit(priority = 2, onlyForDebug = true, description = "MatrixInit")
class MatrixInit : SimpleAppInit() {

  override fun needAsyncInit(): Boolean {
    return true
  }

  override fun asyncOnCreate() {
    Timber.tag(TAG).e("MatrixInit")
    initMatrix()
  }

  private fun initMatrix() {
    val dynamicConfig = DynamicConfigImplDemo()
    val matrixEnable: Boolean = dynamicConfig.isMatrixEnable
    val fpsEnable: Boolean = dynamicConfig.isFpsEnable
    val traceEnable: Boolean = dynamicConfig.isTraceEnable

    MatrixLog.i(TAG, "MatrixApplication.onCreate")

    val builder: Matrix.Builder = Matrix.Builder(mApplication)
    builder.patchListener(TestPluginListener(mApplication))

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
      intent.setClassName(mApplication.packageName, "com.tencent.mm.ui.matrix.ManualDumpActivity")
      val resourceConfig: ResourceConfig = ResourceConfig.Builder()
          .dynamicConfig(dynamicConfig)
          .setAutoDumpHprofMode(mode) //                .setDetectDebuger(true) //matrix test code
          .setNotificationContentIntent(intent)
          .build()
      builder.plugin(ResourcePlugin(resourceConfig))
      ResourcePlugin.activityLeakFixer(mApplication)

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