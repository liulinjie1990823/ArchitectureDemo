package com.llj.architecturedemo.ui.module

import androidx.lifecycle.ViewModel
import com.llj.architecturedemo.ui.activity.MainActivity
import com.llj.architecturedemo.ui.activity.MvvmRequestActivity
import com.llj.architecturedemo.ui.view.MainContractView
import com.llj.architecturedemo.vm.MainContractViewModel
import com.llj.lib.base.di.ViewModelKey
import dagger.Binds

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * ArchitectureDemo
 * describe:
 * @author liulj
 * @date 2018/5/16
 */
@Module
abstract class MvvmRequestActivityModule {

  @Binds
  @IntoMap
  @ViewModelKey(MainContractViewModel::class)
  abstract fun bindViewModel(viewModel: MainContractViewModel): ViewModel
}
