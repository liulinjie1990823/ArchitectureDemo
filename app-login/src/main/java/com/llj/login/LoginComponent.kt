package com.llj.login

import com.llj.component.service.IInject
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/19
 */
@LoginScope
@Component(
    dependencies = [
      com.llj.component.service.MiddleComponent::class //依赖库中的Component，可以获得一些字段
    ],
    modules = [
      AndroidInjectionModule::class,
      AndroidSupportInjectionModule::class,
      LoginComponentBuilder::class, //将所有的Activity,Fragment注册进来
      LoginComponentModule::class
    ])
internal interface LoginComponent : IInject {

//    @Component.Builder
//    interface Builder {
//
//        //提供给AppModule中方法中的Application入参用
//        @BindsInstance
//        fun application(application: Application): Builder
//
//        fun build(): LoginComponent
//    }

}