package com.llj.architecturedemo;

import com.llj.lib.base.di.module.BaseAppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/18
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        BaseAppModule.class,
})
public interface AppComponent {

}
