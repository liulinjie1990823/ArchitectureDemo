package com.llj.login.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.llj.application.AppApplication
import com.llj.application.di.IModule
import com.llj.application.router.CRouter
import com.llj.lib.base.di.IInject
import com.llj.login.di.DaggerLoginComponent

/**
 * ArchitectureDemo.
 * Module之间交互类,也可以通过ARouter来实现。当前类持有dagger中的Component对象。
 * author llj
 * date 2018/8/23
 */
class LoginModule : IComponent, IModule {
    private lateinit var mComponent: IInject

    override fun getName(): String {
        return CRouter.MODULE_LOGIN
    }

    override fun initComponent(application: AppApplication) {
        mComponent = DaggerLoginComponent.builder()
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