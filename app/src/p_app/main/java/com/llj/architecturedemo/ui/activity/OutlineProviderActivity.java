package com.llj.architecturedemo.ui.activity;

import android.graphics.Path;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.florent37.shapeofview.manager.ClipPathManager;
import com.llj.architecturedemo.MainMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.databinding.ActivityOutlineProviderBinding;
import com.llj.application.router.CRouter;
import org.jetbrains.annotations.Nullable;

/**
 * describe 裁剪
 *
 * @author liulinjie
 * @date 2020/4/16 2:12 PM
 */
@Route(path = CRouter.APP_OUTLINE_PROVIDER_ACTIVITY)
public class OutlineProviderActivity extends MainMvcBaseActivity<ActivityOutlineProviderBinding> {

  @Nullable
  @Override
  public ActivityOutlineProviderBinding layoutViewBinding() {
    return ActivityOutlineProviderBinding.inflate(getLayoutInflater());
  }

  @Override
  public int layoutId() {
    return R.layout.activity_outline_provider;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    //mViewBinder.ivClip.setClipToOutline(true);
    //mViewBinder.ivClip.setOutlineProvider(new ViewOutlineProvider() {
    //  @Override
    //  public void getOutline(View view, Outline outline) {
    //    //outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
    //    Path path = new Path();
    //    path.moveTo(view.getWidth(), view.getHeight());
    //    path.lineTo(view.getWidth(), view.getHeight() * 2);
    //    path.lineTo(view.getWidth() * 2, view.getHeight() * 2);
    //    path.lineTo(view.getWidth() * 2, view.getHeight());
    //    path.close();
    //    outline.setConvexPath(path);
    //  }
    //});
    mViewBinder.myShape.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
      @Override
      public Path createClipPath(int width, int height) {
        final Path path = new Path();

        //eg: triangle
        path.moveTo(0, 0);
        path.lineTo((float) (0.5 * width), height);
        path.lineTo(width, 0);
        path.close();

        return path;
      }

      @Override
      public boolean requiresBitmap() {
        return false;
      }
    });
  }

  @Override
  public void initData() {

  }
}
