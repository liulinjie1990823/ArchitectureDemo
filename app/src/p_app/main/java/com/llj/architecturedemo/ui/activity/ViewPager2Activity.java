package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.databinding.ActivityViewpager2Binding;
import org.jetbrains.annotations.Nullable;

/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020/3/22 1:39 PM
 */
public class ViewPager2Activity extends AppMvcBaseActivity {

  private ActivityViewpager2Binding mBinding;

  @Override
  public int layoutId() {
    return R.layout.activity_viewpager2;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {

  }

  @Override
  public void initData() {

  }
}
