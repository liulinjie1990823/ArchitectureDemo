package com.llj.architecturedemo.di

import com.llj.architecturedemo.ui.activity.MainActivity
import com.llj.architecturedemo.ui.activity.MvvmRequestActivity
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
 * @author llj
 * @date 2018/5/16
 */
@Module
internal abstract class MainComponentBuilder {

  @ActivityScope
  @ContributesAndroidInjector(modules = [MvvmRequestActivityModule::class])
  internal abstract fun activity1(): MvvmRequestActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [MainActivityModule::class])
  internal abstract fun activity2(): MainActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [SecondActivityModule::class])
  internal abstract fun activity3(): SecondActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [CircleViewActivityModule::class])
  internal abstract fun activity4(): CircleViewActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [RequestActivityModule::class])
  internal abstract fun activity5(): RequestActivity

  @FragmentScope
  @ContributesAndroidInjector(modules = [VLayoutFragmentModule::class])
  internal abstract fun fragment1(): VLayoutFragment

  @FragmentScope
  @ContributesAndroidInjector(modules = [VLayoutFragment2Module::class])
  internal abstract fun fragment2(): VLayoutFragment2

  @FragmentScope
  @ContributesAndroidInjector(modules = [ScrollableLayoutFragmentModule::class])
  internal abstract fun fragment3(): ScrollableLayoutFragment

  @FragmentScope
  @ContributesAndroidInjector(modules = [MineFragmentModule::class])
  internal abstract fun fragment4(): MineFragment
}