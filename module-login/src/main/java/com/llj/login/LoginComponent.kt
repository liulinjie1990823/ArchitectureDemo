package com.llj.login

import android.app.Activity
import android.app.Application
import com.llj.architecturedemo.LoginComponentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/19
 */
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    LoginActivityBuilder::class, //将所有的activity注册进来
    LoginComponentModule::class
])
interface LoginComponent {

    @Component.Builder
    interface Builder {

        //提供给AppModule中方法中的Application入参用
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): LoginComponent
    }
    //调用该方法才会注入BaseApplication中的@Inject标记的对象
    //    override fun inject(application: BaseApplication)

    fun activityInjector(): DispatchingAndroidInjector<Activity>
}