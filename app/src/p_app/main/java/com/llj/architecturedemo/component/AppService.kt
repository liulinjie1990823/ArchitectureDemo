package com.llj.architecturedemo.component

import android.content.Context
import com.llj.architecturedemo.DaggerAppComponent
import com.llj.component.service.IInject
import com.llj.component.service.IModule
import com.llj.component.service.IService
import com.llj.component.service.MiddleApplication

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2020/4/29
 */
class AppService : IService, IModule {
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
        mComponent = DaggerAppComponent.builder()
                .middleComponent(application.mMiddleComponent)
                .build()
    }

    override fun getComponent(): IInject {
        return checkComponent(mComponent)
    }
}