package com.llj.architecturedemo.di

import com.llj.application.di.AppComponent
import com.llj.component.service.IInject
import com.llj.lib.base.di.ViewModelBuilder
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule


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


@MainScope
@Component(
    dependencies = [
      AppComponent::class
    ],
    modules = [
      ViewModelBuilder::class,
      MainComponentBuilder::class, //将所有的Activity和Fragment注册进来
      MainComponentModule::class,//提供对象给AppComponent使用
      AndroidInjectionModule::class,
      AndroidSupportInjectionModule::class,
    ])
interface MainComponent : IInject {

}
