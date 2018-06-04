package com.llj.architecturedemo;

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
    MainContract.ViewModel provideModel(MainContractViewModel model) {
        return model;
    }


}
