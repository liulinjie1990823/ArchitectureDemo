package com.llj.login

import com.llj.lib.base.di.component.BaseActivityComponent
import com.llj.lib.base.di.scope.ActivityScope
import com.llj.login.ui.activity.PhoneLoginActivity
import com.llj.login.ui.module.PhoneLoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * ArchitectureDemo
 * describe:定义所有的ActivityInjector
 * author liulj
 * date 2018/5/16
 */
@Module(subcomponents = [BaseActivityComponent::class])
internal abstract class LoginActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [PhoneLoginModule::class])
    internal abstract fun contributePhoneLoginActivityInjector(): PhoneLoginActivity


}