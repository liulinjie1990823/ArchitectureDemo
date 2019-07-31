package com.llj.setting;

import com.llj.component.service.ComponentMvcBaseToolbarActivity;

import org.jetbrains.annotations.NotNull;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
public abstract class SettingMvcBaseActivity extends ComponentMvcBaseToolbarActivity {
    @NotNull
    @Override
    public String getModuleName() {
        return "app-setting";
    }
}
