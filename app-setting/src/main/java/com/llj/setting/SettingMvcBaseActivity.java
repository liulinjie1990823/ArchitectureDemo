package com.llj.setting;

import com.llj.component.service.ComponentMvcBaseActivity;

import org.jetbrains.annotations.NotNull;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
public abstract class SettingMvcBaseActivity extends ComponentMvcBaseActivity {
    @NotNull
    @Override
    public String moduleName() {
        return "app-setting";
    }
}
