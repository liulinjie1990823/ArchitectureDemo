package com.llj.lib.base.di.module;

import com.llj.lib.base.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ArchitectureDemo
 * describe:为Component提供相应的实例
 * author liulj
 * date 2018/5/16
 */
@Module
public class BaseAppModule {

    private BaseApplication application;

    public BaseAppModule(BaseApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public BaseApplication provideApplication() {
        return application;
    }

}
