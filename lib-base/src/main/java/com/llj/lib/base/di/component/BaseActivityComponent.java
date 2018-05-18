package com.llj.lib.base.di.component;

import com.llj.lib.base.BaseActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseActivityComponent extends AndroidInjector<BaseActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseActivity> {
    }

}
