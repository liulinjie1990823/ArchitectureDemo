package com.llj.architecturedemo;

import android.arch.lifecycle.ViewModelProviders;

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
    MainContract.View provideView(MainActivity activity) {
        return activity;
    }

    @Provides
    MainContract.ViewModel provideModel(MainActivity activity) {
        return ViewModelProviders.of(activity).get(MainContractViewModel.class);
    }

}
