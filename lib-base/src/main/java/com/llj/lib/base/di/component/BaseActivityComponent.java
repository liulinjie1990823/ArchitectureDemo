package com.llj.lib.base.di.component;

import com.llj.lib.base.MvpBaseActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * ArchitectureDemo
 * describe:用于生成各个activity的Subcomponent，避免重复写
 * author liulj
 * date 2018/5/16
 */
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseActivityComponent extends AndroidInjector<MvpBaseActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MvpBaseActivity> {
    }
}
