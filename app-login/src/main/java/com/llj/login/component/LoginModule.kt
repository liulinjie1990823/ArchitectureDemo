package com.llj.login.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.llj.component.service.MiddleApplication
import com.llj.component.service.IInject
import com.llj.component.service.IModule
import com.llj.login.DaggerLoginComponent

/**
 * ArchitectureDemo.
 * Module之间交互类,也可以通过ARouter来实现。当前类持有dagger中的Component对象。
 * author llj
 * date 2018/8/23
 */
class LoginModule : IComponent, IModule {
    private lateinit var mComponent: IInject

    override fun getName(): String {
        return "app-login"
    }

    override fun initComponent(application: MiddleApplication) {
        mComponent = DaggerLoginComponent.builder()
                .middleComponent(application.mMiddleComponent)
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