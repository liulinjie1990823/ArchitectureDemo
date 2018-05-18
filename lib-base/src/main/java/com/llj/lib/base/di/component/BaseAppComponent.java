package com.llj.lib.base.di.component;

import com.llj.lib.base.BaseApplication;
import com.llj.lib.base.di.module.BaseAppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        BaseAppModule.class,
})
public interface BaseAppComponent {

    BaseApplication baseApplication();

    void inject(BaseApplication application);
}