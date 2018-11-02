package com.llj.architecturedemo

import com.llj.architecturedemo.ui.activity.MainActivity
import com.llj.architecturedemo.ui.activity.RequestActivity
import com.llj.architecturedemo.ui.activity.SecondActivity
import com.llj.architecturedemo.ui.fragment.ScrollableLayoutFragment
import com.llj.architecturedemo.ui.fragment.VlayoutFragment
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
    @ContributesAndroidInjector(modules = [VlayoutFragmentModule::class])
    internal abstract fun contributeVlayoutFragmentInjector(): VlayoutFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ScrollableLayoutFragmentModule::class])
    internal abstract fun contributeScrollableLayoutFragmentInjector(): ScrollableLayoutFragment
}