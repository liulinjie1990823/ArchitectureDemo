package com.llj.architecturedemo

import android.app.Activity
import android.support.v4.app.Fragment
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
            AppComponentBuilder::class, //将所有的Activity和Fragment注册进来
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

    //app工程中的AppComponent，当AppComponent在AppApplication中注册的时候，如果使用inject的方式，
    //会自动注入AppComponentBuilder中声明的activity和fragment到BaseApplication的mActivityInjector
    //和mSupportFragmentInjector中,然而此时组件LoginComponent也进行注册，那么他会再次通过inject方法对
    //BaseApplication的mActivityInjector和mSupportFragmentInjector的字段进行覆盖，导致只有后者
    //LoginComponentBuilder中的activity和fragment，这样app工程里面的activity和fragment运行就会报错
    //由于这种多次注册只是进行覆盖，没有进行合并，所以需要手动获取对应Component中的activityInjector和
    //supportFragmentInjector,参见AppApplication中对应方法的实现

    //调用inject方法会注入BaseApplication中的@Inject标记的mActivityInjector和mSupportFragmentInjector
    //override fun inject(application: BaseApplication)

    fun activityInjector(): DispatchingAndroidInjector<Activity>

    fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>
}
