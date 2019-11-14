package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2019-09-23
 */
@Route(path = CRouter.APP_RUNNABLE_ACTIVITY)
public class RunnableActivity extends AppMvcBaseActivity {

  @Override
  public int layoutId() {
    return R.layout.runnable_activity;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
  }

  @Override
  public void initData() {

  }
}
