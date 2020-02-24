package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.widget.AndroidBug5497Workaround;
import org.jetbrains.annotations.Nullable;

/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020-02-13 15:39
 */

@Route(path = CRouter.APP_ADJUST_RESIZE_ACTIVITY2)
public class AdjustResizeActivity extends AppMvcBaseActivity {

  @Override
  public int layoutId() {
    return R.layout.activity_adjust_resize;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    AndroidBug5497Workaround.assistActivity(this);
    setTranslucentStatusBar(getWindow(), true);


  }

  @Override
  public void initData() {

  }
}
