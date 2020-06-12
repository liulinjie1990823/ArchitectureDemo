package com.llj.lib.base;

import android.content.Context;
import android.content.DialogInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2019-06-25
 */
public class BaseDialogImpl extends BaseDialog {

  public BaseDialogImpl(@NotNull Context context) {
    super(context);
  }

  public BaseDialogImpl(@NotNull Context context, int themeResId) {
    super(context, themeResId);
  }

  @Override
  public int layoutId() {
    return 0;
  }

  @Override
  public void initViews() {

  }

  @Override
  protected void setWindowParam() {

  }

  @Override
  public void onLoadingDialogCancel(@Nullable DialogInterface dialog) {

  }

  @Override
  public void onLoadingDialogDismiss(@Nullable DialogInterface dialog) {

  }
}
