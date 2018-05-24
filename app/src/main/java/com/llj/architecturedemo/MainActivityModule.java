package com.llj.architecturedemo;

import com.llj.lib.base.widget.LoadingDialog;

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
    MainContract.Model provideModel(MainModel model) {
        return model;
    }

    @Provides
    LoadingDialog provideLoadingDialog(MainActivity activity) {
        return new LoadingDialog(activity);
    }
}
