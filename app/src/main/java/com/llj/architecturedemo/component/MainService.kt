package com.llj.architecturedemo.component

import android.content.Context
import android.util.ArrayMap
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.application.AppApplication
import com.llj.application.di.IModule
import com.llj.application.router.CRouter
import com.llj.application.service.ModuleService
import com.llj.architecturedemo.di.DaggerMainComponent
import com.llj.lib.base.di.IInject

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2020/4/29
 */
@Route(path = CRouter.MODULE_MAIN)
class MainService : ModuleService, IModule {
  private lateinit var mComponent: IInject

  override fun init(context: Context) {
    if (context is AppApplication) {
      initComponent(context)
    }
  }

  override fun call(context: Context, event: String, param: ArrayMap<String, String>?) {
    innerCall(context, event, param)
  }

  override fun initComponent(application: AppApplication) {
    mComponent = DaggerMainComponent.builder()
        .appComponent(application.mAppComponent)
        .build()
  }

  override fun getComponent(): IInject {
    return checkComponent(mComponent)
  }
}