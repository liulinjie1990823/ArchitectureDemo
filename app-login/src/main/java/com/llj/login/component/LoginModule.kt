package com.llj.login.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.llj.component.service.ComponentApplication
import com.llj.component.service.IInject
import com.llj.component.service.IModule
import com.llj.login.DaggerLoginComponent

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/23
 */
class LoginModule : IComponent, IModule {
    private lateinit var mComponent: IInject

    override fun getName(): String {
        return "app-login"
    }

    override fun initComponent(application: ComponentApplication) {
        mComponent = DaggerLoginComponent.builder()
                .component(application.mComponent)
                .build()
    }

    override fun getComponent(): IInject {
        return checkComponent(mComponent)
    }

    override fun onCall(cc: CC?): Boolean {
        if (cc == null) {
            return false
        }
        return innerCall(cc)
    }
}