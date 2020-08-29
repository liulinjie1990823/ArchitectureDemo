package com.llj.architecturedemo.component

import android.content.Context
import com.llj.application.AppApplication
import com.llj.application.di.IModule
import com.llj.architecturedemo.di.DaggerMainComponent
import com.llj.component.service.IService
import com.llj.lib.base.di.IInject

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2020/4/29
 */
class AppService : IService, IModule {
  private lateinit var mComponent: IInject

  override fun init(context: Context) {
    if (context is AppApplication) {
      initComponent(context)
    }
  }

  override fun call(context: Context, action: String) {
    innerCall(context, action, IService.sMap)
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