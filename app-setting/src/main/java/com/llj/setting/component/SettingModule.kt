package com.llj.setting.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.llj.application.AppApplication
import com.llj.application.di.IModule
import com.llj.component.service.IInject
import com.llj.component.service.MiddleApplication
import com.llj.setting.di.DaggerSettingComponent

/**
 * ArchitectureDemo.
 * activity.getModuleName -> SettingModule.onCall
 *
 * <pre>
 * override fun androidInjector(): AndroidInjector<Any> {
 * return object : AndroidInjector<Any> {
 * override fun inject(data: Any?) {
 * if (data is MvpBaseActivity<*>) {
 * val mvpBaseActivity = data
 *
 * //调用IModule中的对应action
 * CC.obtainBuilder(mvpBaseActivity.getModuleName())
 * .setContext(mvpBaseActivity)
 * .setActionName(IModule.INJECT_ACTIVITY)
 * .build()
 * .call()
 * } else {
 * val mvpBaseFragment = data as MvpBaseFragment<*>
 *
 * //调用IModule中的对应action
 * CC.obtainBuilder(mvpBaseFragment.getModuleName())
 * .setContext(mvpBaseFragment.context)
 * .addParam("fragment", mvpBaseFragment.tag)
 * .setActionName(IModule.INJECT_FRAGMENT)
 * .build()
 * .call()
 * }
 * }
 * }
 * }
 *
</Any></Any></pre> *
 * author llj
 * date 2019/3/25
 */
class SettingModule : IComponent, IModule {
  private var mComponent: IInject? = null
  override fun getName(): String {
    return "app-setting"
  }

  override fun initComponent(application: AppApplication) {
    mComponent = DaggerSettingComponent.builder()
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