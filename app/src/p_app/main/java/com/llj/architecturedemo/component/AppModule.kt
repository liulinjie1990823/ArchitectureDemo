package com.llj.architecturedemo.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.llj.application.AppApplication
import com.llj.application.di.IModule
import com.llj.architecturedemo.di.DaggerMainComponent
import com.llj.component.service.MiddleApplication
import com.llj.lib.base.di.IInject

/**
 * ArchitectureDemo.
 * 持有dagger的Component对象。
 * 而Component实现了IInject接口，自动生成的类中实现了activityInjector和supportFragmentInjector方法，并持有对应module中注册的activity和fragment.
 * AppApplication的androidInjector中通过cc来调用对应Module中的onCall方法。并间接调用getComponent().activityInjector().inject(activity)。
 *
 *
 *
 * author llj
 * date 2018/8/23
 */
class AppModule : IComponent, IModule {
  private lateinit var mComponent: IInject

  override fun getName(): String {
    return "app"
  }

  override fun initComponent(application: AppApplication) {
    mComponent = DaggerMainComponent.builder()
        .appComponent(application.mAppComponent)
        .build()
  }

  override fun onCall(cc: CC?): Boolean {
    if (cc == null) {
      return false
    }
    return innerCall(cc)
  }

  override fun getComponent(): IInject {
    return checkComponent(mComponent)
  }


}