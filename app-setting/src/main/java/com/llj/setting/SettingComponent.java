package com.llj.setting;

import com.llj.component.service.IInject;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * ArchitectureDemo. dependencies的类会产生对应设置该对象的方法 author llj date 2019/3/25
 */
@SettingScope
@Component(
    dependencies = {com.llj.component.service.MiddleComponent.class},
    modules = {AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        SettingComponentBuilder.class,
        SettingComponentModule.class
    })
public interface SettingComponent extends IInject {

}
