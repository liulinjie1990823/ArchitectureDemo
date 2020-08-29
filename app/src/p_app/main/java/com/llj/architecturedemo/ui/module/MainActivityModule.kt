package com.llj.architecturedemo.ui.module

import com.llj.architecturedemo.ui.activity.MainActivity
import com.llj.architecturedemo.ui.view.MainContractView

import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo
 * describe:
 * @author liulj
 * @date 2018/5/16
 */
@Module
class MainActivityModule {
  @Provides
  internal fun provideView(activity: MainActivity): MainContractView {
    return activity
  }
}
