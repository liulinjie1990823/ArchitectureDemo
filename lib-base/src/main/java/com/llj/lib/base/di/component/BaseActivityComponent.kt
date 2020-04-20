package com.llj.lib.base.di.component

import androidx.viewbinding.ViewBinding
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.mvp.IBasePresenter

import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * ArchitectureDemo
 * describe:用于生成各个activity的Subcomponent，避免重复写，如MainActivitySubcomponent
 * author llj
 * date 2018/5/16
 */
@Subcomponent(modules = [AndroidInjectionModule::class])
interface BaseActivityComponent : AndroidInjector<MvpBaseActivity<ViewBinding, IBasePresenter>> {

  @Subcomponent.Builder
  abstract class Builder : AndroidInjector.Builder<MvpBaseActivity<ViewBinding, IBasePresenter>>()
}
