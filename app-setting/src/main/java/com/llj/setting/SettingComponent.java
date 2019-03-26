package com.llj.setting;

import com.llj.component.service.IInject;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
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
public interface SettingComponent extends IInject {

}
