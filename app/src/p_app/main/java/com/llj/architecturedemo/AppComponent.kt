package com.llj.architecturedemo

import com.llj.component.service.IInject
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * ArchitectureDemo
 *
 *
 * dependencies依赖的类和modules中的Module会自动生成set方法：appComponentModule，middleComponent
 * <pre>
 * {@code
 * public static final class Builder {
 *     private AppComponentModule appComponentModule;
 *
 *     private MiddleComponent middleComponent;
 *
 *     private Builder() {
 *     }
 *
 *     public Builder appComponentModule(AppComponentModule appComponentModule) {
 *         this.appComponentModule = Preconditions.checkNotNull(appComponentModule);
 *         return this;
 *     }
 *
 *     public Builder middleComponent(MiddleComponent middleComponent) {
 *         this.middleComponent = Preconditions.checkNotNull(middleComponent);
 *         return this;
 *     }
 *
 *     public AppComponent build() {
 *         if (appComponentModule == null) {
 *             this.appComponentModule = new AppComponentModule();
 *         }
 *         Preconditions.checkBuilderRequirement(middleComponent, MiddleComponent.class);
 *         return new DaggerAppComponent(appComponentModule, middleComponent);
 *     }
 * }
 * }
 * </pre>
 *
 * IInject中的接口activityInjector和supportFragmentInjector方法会在DaggerAppComponent中实现：
 *
 * <pre>
 * {@code
 *  @Override
 *  public DispatchingAndroidInjector<Activity> activityInjector() {
 *      return DispatchingAndroidInjector_Factory.newInstance(getMapOfClassOfAndProviderOfAndroidInjectorFactoryOf(), Collections.<String, Provider<AndroidInjector.Factory<?>>>emptyMap());}
 *
 *  @Override
 *  public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
 *      return DispatchingAndroidInjector_Factory.newInstance(getMapOfClassOfAndProviderOfAndroidInjectorFactoryOf(), Collections.<String, Provider<AndroidInjector.Factory<?>>>emptyMap());}
 *
 * }
 * </pre>
 * author llj
 * date 2018/5/18
 */



@Singleton
@Component(
        dependencies = [
            com.llj.component.service.MiddleComponent::class
        ],
        modules = [
            AndroidInjectionModule::class,
            AndroidSupportInjectionModule::class,
            AppComponentBuilder::class, //将所有的Activity和Fragment注册进来
            AppComponentModule::class
        ])
interface AppComponent : IInject {

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
    //fun inject(application: BaseApplication)

    //app工程中的AppComponent，当AppComponent在AppApplication中注册的时候，如果使用inject的方式，
    //会自动注入AppComponentBuilder中声明的activity和fragment到BaseApplication的mActivityInjector
    //和mSupportFragmentInjector中,然而此时组件LoginComponent也进行注册，那么他会再次通过inject方法对
    //BaseApplication的mActivityInjector和mSupportFragmentInjector的字段进行覆盖，导致只有后者
    //LoginComponentBuilder中的activity和fragment，这样app工程里面的activity和fragment运行就会报错
    //由于这种多次注册只是进行覆盖，没有进行合并，所以需要手动获取对应Component中的activityInjector和
    //supportFragmentInjector,参见AppApplication中androidInjector()方法的实现

    //调用inject方法会注入BaseApplication中的@Inject标记的mActivityInjector和mSupportFragmentInjector
    //override fun inject(application: BaseApplication)

    //使用组件的话需要注入各自组件中的activity,由于会覆盖，所以不用fun inject(application: BaseApplication)
    //通过下面两个方法，可以获取各自Component的activityInjector和supportFragmentInjector，在AppApplication中返回
    //各自组件的activityInjector和supportFragmentInjector，如下代码

    //    override fun androidInjector(): AndroidInjector<Any> {
    //        return object : AndroidInjector<Any> {
    //            override fun inject(data: Any?) {
    //                if (data is MvpBaseActivity<*>) {
    //                    val mvpBaseActivity = data
    //
    //                    //调用IModule中的对应action
    //                    CC.obtainBuilder(mvpBaseActivity.getModuleName())
    //                            .setContext(mvpBaseActivity)
    //                            .setActionName(IModule.INJECT_ACTIVITY)
    //                            .build()
    //                            .call()
    //                } else {
    //                    val mvpBaseFragment = data as MvpBaseFragment<*>
    //
    //                    //调用IModule中的对应action
    //                    CC.obtainBuilder(mvpBaseFragment.getModuleName())
    //                            .setContext(mvpBaseFragment.context)
    //                            .addParam("fragment", mvpBaseFragment.tag)
    //                            .setActionName(IModule.INJECT_FRAGMENT)
    //                            .build()
    //                            .call()
    //                }
    //            }
    //        }
    //    }

}
