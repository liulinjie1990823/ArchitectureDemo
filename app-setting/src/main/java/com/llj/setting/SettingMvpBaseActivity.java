package com.llj.setting;

import androidx.viewbinding.ViewBinding;
import com.llj.component.service.MiddleMvpBaseActivity;
import com.llj.lib.base.mvp.IBasePresenter;
import org.jetbrains.annotations.NotNull;

/**
 * ArchitectureDemo. describe: author llj date 2019/3/25
 */
public abstract class SettingMvpBaseActivity<P extends IBasePresenter> extends
    MiddleMvpBaseActivity<P, ViewBinding> {

  @NotNull
  @Override
  public String getModuleName() {
    return "app-setting";
  }

}
