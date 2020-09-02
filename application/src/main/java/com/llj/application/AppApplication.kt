package com.llj.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.ArrayMap
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.billy.cc.core.component.CC
import com.didichuxing.doraemonkit.DoraemonKit
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher
import com.llj.application.di.AppComponent
import com.llj.application.di.DaggerAppComponent
import com.llj.application.di.IModule
import com.llj.application.service.ModuleService
import com.llj.component.service.MiddleApplication
import com.llj.component.service.arouter.CJump
import com.llj.component.service.arouter.CRouter
import com.llj.component.service.preference.UserInfoPreference
import com.llj.component.service.vo.UserInfoVo
import com.llj.lib.base.*
import com.llj.lib.base.config.JumpConfig
import com.llj.lib.base.config.ToolbarConfig
import com.llj.lib.base.config.UserInfoConfig
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter
import com.llj.lib.image.loader.ImageLoader
import com.llj.lib.image.loader.engine.fresco.FrescoEngine
import com.llj.lib.image.loader.engine.fresco.FrescoUtils
import com.llj.lib.jump.api.JumpHelp
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat
import com.llj.lib.tracker.Tracker
import com.llj.lib.tracker.TrackerConfig
import com.llj.lib.utils.helper.Utils
import com.llj.lib.webview.manager.IWebViewClient
import com.llj.lib.webview.manager.WebViewConfig
import com.llj.lib.webview.manager.WebViewManager
import com.llj.socialization.SocialConstants
import com.llj.socialization.init.SocialConfig
import com.llj.socialization.init.SocialManager
import com.sankuai.erp.component.appinit.api.AppInitManager
import com.sankuai.erp.component.appinit.common.AppInitCallback
import com.sankuai.erp.component.appinit.common.AppInitItem
import com.sankuai.erp.component.appinit.common.ChildInitTable
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.smtt.sdk.WebView
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber


/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/18
 */
open class AppApplication : Application(), HasAndroidInjector {

  val TAG: String = this.javaClass.simpleName

  lateinit var mAppComponent: AppComponent

  companion object {
    lateinit var mUserInfoVo: UserInfoVo //用户信息
  }

  override fun onCreate() {
    super.onCreate()
    Utils.init(this)
    mAppComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    AppInitManager.get().init(this, object : AppInitCallback {
      override fun onInitStart(isMainProcess: Boolean, processName: String?) {
      }

      override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
      }

      override fun getCoordinateAheadOfMap(): MutableMap<String, String>? {
        return null
      }

      override fun onInitFinished(isMainProcess: Boolean, processName: String?, childInitTableList: MutableList<ChildInitTable>?, appInitItemList: MutableList<AppInitItem>?) {
      }
    })
  }



  override fun androidInjector(): AndroidInjector<Any> {
    return object : AndroidInjector<Any> {
      override fun inject(data: Any?) {
        when (data) {
          is MvpBaseActivity<*, *> -> {
            //调用IModule中的对应action

            val moduleService: ModuleService = ARouter.getInstance().build(data.getModuleName())
                .navigation() as ModuleService
            moduleService.call(data, IModule.INJECT_ACTIVITY, null)
//            CC.obtainBuilder(data.getModuleName())
//                .setContext(data)
//                .setActionName(IModule.INJECT_ACTIVITY)
//                .build()
//                .call()
          }
          is MvcBaseActivity<*> -> {
            //调用IModule中的对应action
            val moduleService: ModuleService = ARouter.getInstance().build(data.getModuleName())
                .navigation() as ModuleService
            moduleService.call(data, IModule.INJECT_ACTIVITY, null)
//            CC.obtainBuilder(data.getModuleName())
//                .setContext(data)
//                .setActionName(IModule.INJECT_ACTIVITY)
//                .build()
//                .call()
          }
          is MvpBaseFragment<*, *> -> {
            //调用IModule中的对应action
            val moduleService: ModuleService = ARouter.getInstance().build(data.getModuleName())
                .navigation() as ModuleService
            val arrayMap = ArrayMap<String, String>()
            arrayMap["fragment"] = data.tag
            moduleService.call(data.context!!, IModule.INJECT_FRAGMENT, arrayMap)

//            CC.obtainBuilder(data.getModuleName())
//                .setContext(data.context)
//                .addParam("fragment", data.tag)
//                .setActionName(IModule.INJECT_FRAGMENT)
//                .build()
//                .call()
          }
        }
      }
    }
  }

  override fun onTerminate() {
    super.onTerminate()
    AppInitManager.get().onTerminate()
  }

  override fun onConfigurationChanged(newConfig: Configuration?) {
    super.onConfigurationChanged(newConfig)
    AppInitManager.get().onConfigurationChanged(newConfig)
  }

  override fun onLowMemory() {
    super.onLowMemory()
    AppInitManager.get().onLowMemory()
  }

  override fun onTrimMemory(level: Int) {
    super.onTrimMemory(level)
    AppInitManager.get().onTrimMemory(level)
  }
}
