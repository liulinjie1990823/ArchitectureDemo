package com.llj.architecturedemo;

import com.llj.component.service.ComponentApplication;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/18
 */
public class AppApplication extends ComponentApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void injectApp() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);


    }
}
