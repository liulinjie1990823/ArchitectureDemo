package com.llj.application

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.res.Configuration
import android.util.ArrayMap
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.application.di.AppComponent
import com.llj.application.di.DaggerAppComponent
import com.llj.application.di.IModule
import com.llj.application.service.ModuleService
import com.llj.application.vo.UserInfoVo
import com.llj.lib.base.MvcBaseActivity
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.utils.helper.Utils
import com.sankuai.erp.component.appinit.api.AppInitManager
import com.sankuai.erp.component.appinit.common.AppInitCallback
import com.sankuai.erp.component.appinit.common.AppInitItem
import com.sankuai.erp.component.appinit.common.ChildInitTable
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector


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
        return applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
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
          }
          is MvcBaseActivity<*> -> {
            //调用IModule中的对应action
            val moduleService: ModuleService = ARouter.getInstance().build(data.getModuleName())
                .navigation() as ModuleService
            moduleService.call(data, IModule.INJECT_ACTIVITY, null)
          }
          is MvpBaseFragment<*, *> -> {
            //调用IModule中的对应action
            val moduleService: ModuleService = ARouter.getInstance().build(data.getModuleName())
                .navigation() as ModuleService
            val arrayMap = ArrayMap<String, String>()
            arrayMap["fragment"] = data.tag
            moduleService.call(data.context!!, IModule.INJECT_FRAGMENT, arrayMap)

          }
        }
      }
    }
  }

  override fun onTerminate() {
    super.onTerminate()
    AppInitManager.get().onTerminate()
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
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
