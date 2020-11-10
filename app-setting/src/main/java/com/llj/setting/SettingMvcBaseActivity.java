package com.llj.setting;

import com.llj.component.service.PlatformMvcBaseToolbarActivity;

import com.llj.application.router.CRouter;
import org.jetbrains.annotations.NotNull;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
public abstract class SettingMvcBaseActivity extends PlatformMvcBaseToolbarActivity {
    @NotNull
    @Override
    public String getModuleName() {
        return CRouter.MODULE_SETTING;
    }
}
