package com.llj.architecturedemo.module;

import android.arch.lifecycle.ViewModelProviders;

import com.llj.architecturedemo.ui.activity.MainActivity;
import com.llj.architecturedemo.view.MainContractView;
import com.llj.architecturedemo.vm.MainContractViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
@Module
public class MainActivityModule {
    @Provides
    MainContractView provideView(MainActivity activity) {
        return activity;
    }

    @Provides
    MainContractViewModel provideModel(MainActivity activity) {
        return ViewModelProviders.of(activity).get(MainContractViewModel.class);
    }

}
