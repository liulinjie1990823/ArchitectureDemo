package com.llj.lib.base.di.component

import com.llj.lib.base.MvpBaseFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * ArchitectureDemo
 * describe:用于生成各个fragment的Subcomponent，避免重复写
 * author llj
 * date 2018/5/16
 */
@Subcomponent(modules = [AndroidSupportInjectionModule::class])
interface BaseFragmentComponent : AndroidInjector<MvpBaseFragment<*>> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MvpBaseFragment<*>>()
}
