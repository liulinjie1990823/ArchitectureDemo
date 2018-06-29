package com.llj.architecturedemo;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.llj.architecturedemo.vm.MainContractViewModel;
import com.llj.lib.base.di.ViewModelKey;
import com.llj.lib.base.mvp.MyViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/4
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainContractViewModel.class)
    abstract ViewModel bindProductListViewModel(MainContractViewModel listViewModel);
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MyViewModelProvider factory);
}
