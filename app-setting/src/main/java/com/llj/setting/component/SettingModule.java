package com.llj.setting.component;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.IComponent;
import com.llj.component.service.MiddleApplication;
import com.llj.component.service.IInject;
import com.llj.component.service.IModule;
import com.llj.setting.DaggerSettingComponent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
public class SettingModule implements IComponent, IModule {
    private IInject mComponent;

    @Override
    public String getName() {
        return "app-setting";
    }

    @Override
    public boolean onCall(CC cc) {
        if (cc == null) {
            return false;
        }
        return innerCall(cc);
    }

    @Override
    public void initComponent(@NotNull MiddleApplication application) {
        mComponent = DaggerSettingComponent.builder()
                .middleComponent(application.mMiddleComponent)
                .build();
    }

    @NotNull
    @Override
    public IInject getComponent() {
        return checkComponent(mComponent);
    }

    @Override
    public boolean innerCall(@NotNull CC cc) {
        return IModule.DefaultImpls.innerCall(this, cc);
    }

    @NotNull
    @Override
    public IInject checkComponent(@Nullable IInject component) {
        return IModule.DefaultImpls.checkComponent(this, component);
    }
}
