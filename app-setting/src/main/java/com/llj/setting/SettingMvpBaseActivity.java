package com.llj.setting;

import androidx.viewbinding.ViewBinding;
import com.llj.component.service.MiddleMvpBaseActivity;
import com.llj.application.router.CRouter;
import com.llj.lib.base.mvp.IBasePresenter;
import org.jetbrains.annotations.NotNull;

/**
 * describe
 *
 * @author liulinjie
 * @date 2020/8/30 1:20 PM
 */
public abstract class SettingMvpBaseActivity<P extends IBasePresenter> extends
    MiddleMvpBaseActivity<ViewBinding, P> {

  @NotNull
  @Override
  public String getModuleName() {
    return CRouter.MODULE_SETTING;
  }

}
