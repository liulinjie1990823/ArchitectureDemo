package com.llj.architecturedemo

import com.llj.architecturedemo.ui.activity.MainActivity
import com.llj.architecturedemo.ui.activity.RequestActivity
import com.llj.architecturedemo.ui.activity.SecondActivity
import com.llj.architecturedemo.ui.fragment.MineFragment
import com.llj.architecturedemo.ui.fragment.ScrollableLayoutFragment
import com.llj.architecturedemo.ui.fragment.VLayoutFragment
import com.llj.architecturedemo.ui.fragment.VLayoutFragment2
import com.llj.architecturedemo.ui.module.*
import com.llj.lib.base.di.component.BaseActivityComponent
import com.llj.lib.base.di.component.BaseFragmentComponent
import com.llj.lib.base.di.scope.ActivityScope
import com.llj.lib.base.di.scope.FragmentScope
import com.llj.widget.ui.activity.CircleViewActivity
import com.llj.widget.ui.module.CircleViewActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * ArchitectureDemo
 * describe:定义所有的ActivityInjector
 * author llj
 * date 2018/5/16
 */
@Module(subcomponents = [BaseActivityComponent::class, BaseFragmentComponent::class])
internal abstract class AppComponentBuilder {

  //    private final class MainActivitySubcomponentImpl implements AppComponentBuilder_ContributeMainActivityInjector$app_debug.MainActivitySubcomponent {
//        private final MainActivity arg0;
//
//        private final MainActivityModule mainActivityModule;
//
//        private MainActivitySubcomponentImpl(MainActivityModule mainActivityModuleParam,
//        MainActivity arg0Param) {
//            this.arg0 = arg0Param;
//            this.mainActivityModule = mainActivityModuleParam;
//        }
//
//        private MainContractView getMainContractView() {
//            return MainActivityModule_ProvideView$app_debugFactory.provideView$app_debug(mainActivityModule, arg0);}
//
//        private MainPresenter getMainPresenter() {
//            return new MainPresenter(DaggerAppComponent.this.homeRepositoryProvider.get(), getMainContractView());}
//
//        @Override
//        public void inject(MainActivity arg0) {
//            injectMainActivity(arg0);}
//
//        private MainActivity injectMainActivity(MainActivity instance) {
//            MvpBaseActivity_MembersInjector.injectMPresenter(instance, getMainPresenter());
//            return instance;
//        }
//    }
  @ActivityScope
  @ContributesAndroidInjector(modules = [MainActivityModule::class])
  internal abstract fun contributeMainActivityInjector(): MainActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [SecondActivityModule::class])
  internal abstract fun contributeSecondActivityInjector(): SecondActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [CircleViewActivityModule::class])
  internal abstract fun contributeCircleViewActivityInjector(): CircleViewActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [RequestActivityModule::class])
  internal abstract fun contributeRequestActivityInjector(): RequestActivity

  @FragmentScope
  @ContributesAndroidInjector(modules = [VLayoutFragmentModule::class])
  internal abstract fun contributeVlayoutFragmentInjector(): VLayoutFragment


  @FragmentScope
  @ContributesAndroidInjector(modules = [VLayoutFragment2Module::class])
  internal abstract fun contributeVlayoutFragment2Injector(): VLayoutFragment2

  @FragmentScope
  @ContributesAndroidInjector(modules = [ScrollableLayoutFragmentModule::class])
  internal abstract fun contributeScrollableLayoutFragmentInjector(): ScrollableLayoutFragment

  @FragmentScope
  @ContributesAndroidInjector(modules = [MineFragmentModule::class])
  internal abstract fun contributeMineFragmentInjector(): MineFragment
}