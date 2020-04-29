package com.llj.login.component

import android.content.Context
import com.llj.component.service.IInject
import com.llj.component.service.IModule
import com.llj.component.service.IService
import com.llj.component.service.MiddleApplication
import com.llj.login.DaggerLoginComponent

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2020/4/29
 */
class LoginService : IService, IModule {
    private lateinit var mComponent: IInject

    override fun init(context: Context) {
        if (context is MiddleApplication) {
            initComponent(context)
        }
    }

    override fun call(context: Context, action: String) {
        innerCall(context, action, IService.sMap)
    }

    override fun initComponent(application: MiddleApplication) {
        mComponent = DaggerLoginComponent.builder()
                .middleComponent(application.mMiddleComponent)
                .build()
    }

    override fun getComponent(): IInject {
        return checkComponent(mComponent)
    }
}