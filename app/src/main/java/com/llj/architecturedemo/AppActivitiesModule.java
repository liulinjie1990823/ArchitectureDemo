package com.llj.architecturedemo;

import com.llj.lib.base.di.component.BaseActivityComponent;
import com.llj.lib.base.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * ArchitectureDemo
 * describe:定义所有的ActivityInjector
 * author liulj
 * date 2018/5/16
 */
@Module(subcomponents = {BaseActivityComponent.class})
abstract class AppActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivityInjector();
}