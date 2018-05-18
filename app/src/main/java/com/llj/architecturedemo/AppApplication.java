package com.llj.architecturedemo;

import com.llj.lib.base.BaseApplication;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/18
 */
public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void injectApp() {
        DaggerAppComponent.builder()
                .build()
                .inject(this);


    }
}
