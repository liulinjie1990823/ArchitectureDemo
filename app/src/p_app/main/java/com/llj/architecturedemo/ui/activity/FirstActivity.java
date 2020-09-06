package com.llj.architecturedemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.MainMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.application.router.CRouter;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2019-05-23
 */
@Route(path = CRouter.APP_FIRST_ACTIVITY)
public class FirstActivity extends MainMvcBaseActivity {

  @Override
  public int layoutId() {
    return R.layout.first_activity;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    findViewById(R.id.tv_first).setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View v) {
        Intent intent = new Intent(mContext, SecondActivity.class);
        mContext.startActivity(intent);
      }
    });
  }

  @Override
  public void initData() {

  }
}
