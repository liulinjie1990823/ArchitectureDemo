package com.llj.setting.di

import com.llj.lib.base.di.component.BaseActivityComponent
import com.llj.lib.base.di.component.BaseFragmentComponent
import com.llj.lib.base.di.scope.ActivityScope
import com.llj.setting.ui.activity.QrCodeActivity
import com.llj.setting.ui.activity.SettingActivity
import com.llj.setting.ui.module.SettingActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * ArchitectureDemo. describe: author llj date 2019/3/25
 */
@Module(subcomponents = [BaseActivityComponent::class, BaseFragmentComponent::class])
internal abstract class SettingComponentBuilder {
  @ActivityScope
  @ContributesAndroidInjector(modules = [SettingActivityModule::class])
  abstract fun qrCodeActivityInjector(): QrCodeActivity?

  @ActivityScope
  abstract fun SettingActivity(): SettingActivity
}