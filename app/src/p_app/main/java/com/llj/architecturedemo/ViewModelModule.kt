package com.llj.architecturedemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.llj.architecturedemo.vm.MainContractViewModel
import com.llj.lib.base.di.ViewModelKey
import com.llj.lib.base.mvp.MyViewModelProvider

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/4
 */
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainContractViewModel::class)
    internal abstract fun bindProductListViewModel(listViewModel: MainContractViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: MyViewModelProvider): ViewModelProvider.Factory
}
