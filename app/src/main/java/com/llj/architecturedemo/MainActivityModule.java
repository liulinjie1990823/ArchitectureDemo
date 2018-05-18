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
    static MainContract.View provideView(MainActivity activity) {
        return activity;
    }

    @Provides
    static MainContract.Model provideModel(MainModel model) {
        return model;
    }
}
