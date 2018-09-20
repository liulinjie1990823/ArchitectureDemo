package com.llj.architecturedemo

import android.app.Activity
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/18
 */
@Singleton
@Component(
        dependencies = [
            com.llj.component.service.Component::class
        ],
        modules = [
            AndroidInjectionModule::class,
            AndroidSupportInjectionModule::class,
            ActivityBuilder::class, //将所有的activity注册进来
            AppComponentModule::class
           ])
interface AppComponent {

//    @Component.Builder
//    interface Builder {
//
//        //提供给AppModule中方法中的Application入参用
//        @BindsInstance
//        fun application(application: Application): Builder
//
//        fun build(): AppComponent
//    }

    //调用该方法才会注入BaseApplication中的@Inject标记的对象
    //    override fun inject(application: BaseApplication)

    fun activityInjector(): DispatchingAndroidInjector<Activity>
}
