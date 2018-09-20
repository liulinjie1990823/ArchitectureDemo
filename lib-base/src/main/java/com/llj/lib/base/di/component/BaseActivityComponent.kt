package com.llj.lib.base.di.component

import com.llj.lib.base.MvpBaseActivity

import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * ArchitectureDemo
 * describe:用于生成各个activity的Subcomponent，避免重复写
 * author llj
 * date 2018/5/16
 */
@Subcomponent(modules = [AndroidInjectionModule::class])
interface BaseActivityComponent : AndroidInjector<MvpBaseActivity<*>> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MvpBaseActivity<*>>()
}
