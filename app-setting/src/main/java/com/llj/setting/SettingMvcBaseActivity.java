package com.llj.setting;

import androidx.viewbinding.ViewBinding;
import com.llj.application.router.CRouter;
import com.llj.component.service.PlatformMvcBaseToolbarActivity;
import org.jetbrains.annotations.NotNull;

/**
 * ArchitectureDemo. describe: author llj date 2019/3/25
 */
public abstract class SettingMvcBaseActivity<V extends ViewBinding> extends
    PlatformMvcBaseToolbarActivity<V> {

  @NotNull
  @Override
  public String getModuleName() {
    return CRouter.MODULE_SETTING;
  }
}
