package com.llj.architecturedemo

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/18
 */
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ActivityBuilder::class, //将所有的activity注册进来
    AppModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<AppApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    //调用该方法才会注入BaseApplication中的@Inject标记的对象
    override fun inject(application: AppApplication)
}
