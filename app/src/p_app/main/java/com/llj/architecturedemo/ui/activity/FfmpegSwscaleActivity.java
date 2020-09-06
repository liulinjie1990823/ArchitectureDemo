package com.llj.architecturedemo.ui.activity;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.llj.architecturedemo.vo.DataVo;
import com.llj.application.router.CRouter;
import com.llj.lib.base.help.FilePathHelper;
import java.io.File;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/**
 * ArchitectureDemo.
 *
 * <p>describe:
 *
 * @author llj
 * @date 2020/5/11
 */
@Route(path = CRouter.APP_FFMPEG_SWSCALE_ACTIVITY)
public class FfmpegSwscaleActivity extends DataListActivity {

  private File mOutput;


  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    super.initViews(savedInstanceState);
    mOutput = new File(FilePathHelper.CACHE_PATH + "/" + "test.jpg");
  }

  @Override
  public void getData(@NotNull ArrayList<DataVo> data) {
    data.add(new DataVo("fast_bilinear", fast_bilinear));
    data.add(new DataVo("bilinear", bilinear));
    data.add(new DataVo("bicubic", bicubic));
    data.add(new DataVo("experimental", experimental));
    data.add(new DataVo("neighbor", neighbor));
    data.add(new DataVo("area", area));
    data.add(new DataVo("bicublin", bicublin));
    data.add(new DataVo("gauss", gauss));
    data.add(new DataVo("sinc", sinc));
    data.add(new DataVo("lanczos", lanczos));
    data.add(new DataVo("spline", spline));
  }

  private long mCurrent;

  @Override
  public void onClick(@NotNull View view, @NotNull DataVo item) {
    int rc = -1;
    long temp = 0;
    String command = null;

    mOutput = new File(FilePathHelper.CACHE_PATH + "/" + item.getName() + ".jpg");
    mCurrent = System.currentTimeMillis();
    command = "-i " + FilePathHelper.CACHE_PATH + "/" + "test10086.jpg"
        //            + " -s 936x911"
        + " -vf scale=-1:911,crop=936:911,transpose=1"
        //+ " -vf scale=936:-1,transpose=1"
        + " -sws_flags " + item.getName() + " "
        + mOutput.getAbsolutePath();
    rc = FFmpeg.execute(command);

    temp = System.currentTimeMillis() - mCurrent;

    Timber.tag(mTagLog).i(command);

    if (rc == RETURN_CODE_SUCCESS) {
      showLongToast("Command execution completed successfully." + temp + "ms");
      Timber.tag(mTagLog).i("Command execution completed successfully." + temp + "ms");
      Log.i(Config.TAG, "Command execution completed successfully.");
    } else if (rc == RETURN_CODE_CANCEL) {
      showLongToast("Command execution cancelled by user.");
      Log.i(Config.TAG, "Command execution cancelled by user.");
    } else {
      showLongToast("Command execution failed with rc=%d and the output below." + rc);
      Log.i(Config.TAG,
          String.format("Command execution failed with rc=%d and the output below.", rc));
      Config.printLastCommandOutput(Log.INFO);
    }


  }

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
}
