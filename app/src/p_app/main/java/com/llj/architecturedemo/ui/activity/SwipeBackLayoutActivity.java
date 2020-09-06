package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.gw.swipeback.SwipeBackLayout;
import com.llj.architecturedemo.MainMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.R2.id;
import com.llj.application.router.CRouter;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe: author llj date 2018/12/17
 */
@Route(path = CRouter.APP_SWIPE_BACK_LAYOUT_ACTIVITY)
public class SwipeBackLayoutActivity extends MainMvcBaseActivity {

  @BindView(id.swipeBackLayout) SwipeBackLayout mSwipeBackLayout;

  @Override
  public void initData() {
  }

  @Override
  public void initViews(@Nullable Bundle bundle) {
  }

  @Override
  public int layoutId() {
    return R.layout.activity_swipe_back_layout;
  }

}
