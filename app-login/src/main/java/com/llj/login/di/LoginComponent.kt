package com.llj.login.di

import com.llj.application.di.AppComponent
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
      AppComponent::class //依赖库中的Component，可以获得一些字段
    ],
    modules = [
      AndroidInjectionModule::class,
      AndroidSupportInjectionModule::class,
      LoginComponentBuilder::class, //将所有的Activity,Fragment注册进来
      LoginComponentModule::class
    ])
internal interface LoginComponent : IInject {


}