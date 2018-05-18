package com.llj.architecturedemo;

import com.llj.lib.base.di.component.BaseActivityComponent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AllActivitysModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivitytInjector();


}