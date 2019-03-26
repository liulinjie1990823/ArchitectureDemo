package com.llj.architecturedemo.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.llj.architecturedemo.DaggerAppComponent
import com.llj.component.service.ComponentApplication
import com.llj.component.service.IInject
import com.llj.component.service.IModule

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/23
 */
class AppModule : IComponent,IModule {
    private lateinit var mComponent: IInject

    override fun getName(): String {
        return "app"
    }

    override fun initComponent(application: ComponentApplication) {
        mComponent = DaggerAppComponent.builder()
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