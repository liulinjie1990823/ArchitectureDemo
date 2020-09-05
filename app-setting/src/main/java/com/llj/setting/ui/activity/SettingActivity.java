package com.llj.setting.ui.activity;

import android.os.Bundle;
import android.view.View;
import butterknife.BindViews;
import butterknife.internal.DebouncingOnClickListener;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.application.router.CRouter;
import com.llj.setting.R;
import com.llj.setting.R2;
import com.llj.setting.SettingMvcBaseActivity;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe:设置界面
 *
 * @author llj
 * @date 2019-07-17
 */
@Route(path = CRouter.SETTING_SETTING_ACTIVITY)
public class SettingActivity extends SettingMvcBaseActivity {

  @BindViews({R2.id.ll_switch_environment
      , R2.id.ll_data
      , R2.id.ll_association
      , R2.id.ll_clear_cache
      , R2.id.ll_feedback
      , R2.id.ll_about
      , R2.id.ll_private
      , R2.id.ll_official_wechat
      , R2.id.ll_service
  }) List<View> mViews;

  @Override
  public int layoutId() {
    return R.layout.setting_setting_activity;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    //        setText(mTvTbTitle, "设置");
    DebouncingOnClickListener onClickListener = new DebouncingOnClickListener() {
      @Override
      public void doClick(View v) {
        if (v.getId() == R.id.ll_switch_environment) {
        } else if (v.getId() == R.id.ll_data) {
        } else if (v.getId() == R.id.ll_association) {
        } else if (v.getId() == R.id.ll_clear_cache) {
        } else if (v.getId() == R.id.ll_feedback) {
        } else if (v.getId() == R.id.ll_about) {
        } else if (v.getId() == R.id.ll_private) {
        } else if (v.getId() == R.id.ll_official_wechat) {
        } else if (v.getId() == R.id.ll_service) {
        }

      }
    };
    for (View view : mViews) {
      view.setOnClickListener(onClickListener);
    }
    //AFragmentUtils
    //    .replaceFragment(getSupportFragmentManager(), R.id.container,
    //        Flutter.createFragment("setting"));
  }

  @Override
  public void initData() {

  }
}
