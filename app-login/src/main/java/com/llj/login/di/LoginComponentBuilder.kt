package com.llj.login.di

import com.llj.lib.base.di.scope.ActivityScope
import com.llj.lib.base.di.scope.FragmentScope
import com.llj.login.ui.activity.PhoneLoginActivity
import com.llj.login.ui.fragment.PasswordLoginFragment
import com.llj.login.ui.module.PasswordLoginFragmentModule
import com.llj.login.ui.module.PhoneLoginActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * ArchitectureDemo
 * describe:定义所有的ActivityInjector
 * author llj
 * date 2018/5/16
 */
@Module
internal abstract class LoginComponentBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [PhoneLoginActivityModule::class])
    internal abstract fun contributePhoneLoginActivityInjector(): PhoneLoginActivity


    @FragmentScope
    @ContributesAndroidInjector(modules = [PasswordLoginFragmentModule::class])
    internal abstract fun contributePasswordLoginFragmentInjector(): PasswordLoginFragment
}