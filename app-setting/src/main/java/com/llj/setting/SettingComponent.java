package com.llj.setting;

import android.app.Activity;
import android.support.v4.app.Fragment;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
@Singleton
@Component(
        dependencies = {com.llj.component.service.Component.class},
        modules = {AndroidInjectionModule.class,
                AndroidSupportInjectionModule.class,
                SettingComponentBuilder.class,
                SettingComponentModule.class
        })
public interface SettingComponent {

    DispatchingAndroidInjector<Activity> activityInjector();

    DispatchingAndroidInjector<Fragment> supportFragmentInjector();
}
