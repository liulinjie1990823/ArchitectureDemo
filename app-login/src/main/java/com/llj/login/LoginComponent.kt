package com.llj.login

import com.llj.component.service.IInject
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/19
 */
@Singleton
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
    //调用该方法才会注入BaseApplication中的@Inject标记的对象
    //    override fun inject(application: BaseApplication)

}