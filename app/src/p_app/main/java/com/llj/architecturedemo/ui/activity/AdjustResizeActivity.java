package com.llj.architecturedemo.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.databinding.ActivityAdjustResizeBinding;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.listeners.KeyboardStateObserver;
import com.llj.lib.base.listeners.KeyboardStateObserver.OnKeyboardVisibilityListener;
import org.jetbrains.annotations.Nullable;

/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020-02-13 15:39
 */

@SuppressWarnings("ConstantConditions")
@Route(path = CRouter.APP_ADJUST_RESIZE_ACTIVITY2)
public class AdjustResizeActivity extends AppMvcBaseActivity<ActivityAdjustResizeBinding> {

  @Nullable
  @Override
  public ActivityAdjustResizeBinding layoutViewBinding() {
    return ActivityAdjustResizeBinding.inflate(getLayoutInflater());
  }

  @Override
  public int layoutId() {
    return R.layout.activity_adjust_resize;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    //mBinding = ActivityAdjustResizeBinding.inflate(getLayoutInflater());
    mViewBinder.tvText.setText("dadadadadadadadadadada");

    getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);
    //AndroidBug5497Workaround.assistActivity(this);
    setTranslucentStatusBar(getWindow(), true);

    KeyboardStateObserver.getKeyboardStateObserver(getWindow()).setKeyboardVisibilityListener(
        new OnKeyboardVisibilityListener() {
          @Override
          public void onKeyboardShow() {
          }

          @Override
          public void onKeyboardHide() {
          }
        });

    mViewBinder.ivClose
        .setImageBitmap(tintImage(BitmapFactory.decodeResource(getResources(), R.drawable.test1),
            getCompatColor(mContext, R.color.darkslateblue)));

  }

  public Bitmap tintImage(Bitmap bitmap, int color) {
    Paint paint = new Paint();
    paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    Bitmap bitmapResult = Bitmap
        .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmapResult);
    canvas.drawBitmap(bitmap, 0, 0, paint);
    return bitmapResult;
  }

  @Override
  public void initData() {

  }
}
