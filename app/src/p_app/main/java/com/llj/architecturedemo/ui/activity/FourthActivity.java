package com.llj.architecturedemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import butterknife.internal.DebouncingOnClickListener;
import com.llj.architecturedemo.MainMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.db.entity.MobileEntity;
import com.llj.architecturedemo.ui.view.SecondView;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2019-05-23
 */
public class FourthActivity extends MainMvcBaseActivity<ViewBinding> implements SecondView {

  @Override
  public int layoutId() {
    return R.layout.second_activity;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    TextView text = findViewById(R.id.tv_second);
    text.setText("FourthActivity");
    findViewById(R.id.tv_second).setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View v) {
        Intent intent = new Intent(mContext, FifthActivity.class);
        mContext.startActivity(intent);
      }
    });
  }

  @Override
  public void initData() {

  }

  @Override
  public void toast(@Nullable MobileEntity mobile) {

  }
}
