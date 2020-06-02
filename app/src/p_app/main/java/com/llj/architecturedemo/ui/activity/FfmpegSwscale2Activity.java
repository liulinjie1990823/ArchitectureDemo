package com.llj.architecturedemo.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.databinding.ActivityFfmpegSwscale2Binding;
import com.llj.component.service.arouter.CRouter;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo.
 *
 * <p>describe:
 *
 * @author llj
 * @date 2020/5/11
 */
@Route(path = CRouter.APP_FFMPEG_SWSCALE2_ACTIVITY)
public class FfmpegSwscale2Activity extends AppMvcBaseActivity<ActivityFfmpegSwscale2Binding> {


  public static final int fast_bilinear = 0;
  public static final int bilinear      = 1;
  public static final int bicubic       = 2;
  public static final int experimental  = 3;
  public static final int neighbor      = 4;
  public static final int area          = 5;
  public static final int bicublin      = 6;
  public static final int gauss         = 7;
  public static final int sinc          = 8;
  public static final int lanczos       = 9;
  public static final int spline        = 10;

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {

    Options opts = new Options();
    opts.inSampleSize = 4;
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_10087, opts);

    mViewBinder.ivImage.setImageBitmap(bitmap);
  }

  @Override
  public void initData() {

  }
}
