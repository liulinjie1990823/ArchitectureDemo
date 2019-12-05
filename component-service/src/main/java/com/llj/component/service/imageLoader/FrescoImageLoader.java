package com.llj.component.service.imageLoader;

import androidx.annotation.Nullable;
import com.facebook.drawee.view.GenericDraweeView;
import com.llj.lib.image.loader.ICustomImageLoader;

/**
 * describe:
 *
 * @author llj
 * @date 2018/8/14
 */
public class FrescoImageLoader implements ICustomImageLoader<GenericDraweeView> {

  private static ICustomImageLoader<GenericDraweeView> sImageLoader;

  private FrescoImageLoader() {
  }

  public static ICustomImageLoader<GenericDraweeView> getInstance() {
    synchronized (FrescoImageLoader.class) {
      if (sImageLoader == null) {
        sImageLoader = new FrescoImageLoader();
      }
    }
    return sImageLoader;
  }

  //<editor-fold desc="加载本地图片">

  @Override
  public void loadImage(GenericDraweeView view, int resId, int width, int height) {
    FrescoUtils.setController(resId, width, height, view);
  }

  //resId isCircle
  @Override
  public void loadImage(GenericDraweeView view, int resId, int width, int height,
      boolean isCircle) {
    FrescoUtils.setController(resId, width, height, isCircle, false, view);
  }

  @Override
  public void loadImage(GenericDraweeView view, int resId, int width, int height,
      boolean isCircle,
      boolean autoPlayAnimations) {
    FrescoUtils.setController(resId, width, height, isCircle, autoPlayAnimations, view);
  }

  @Override
  public void loadImage(GenericDraweeView view, int resId, int width, int height,
      boolean isCircle, int borderColor, float borderWidth,
      boolean autoPlayAnimations) {
    FrescoUtils
        .setController(resId, width, height, isCircle, borderColor, borderWidth, autoPlayAnimations,
            view);
  }

  //resId radii
  @Override
  public void loadImage(GenericDraweeView view, int resId, int width, int height,
      float[] radii) {
    FrescoUtils.setController(resId, width, height, radii, false, view);
  }

  @Override
  public void loadImage(GenericDraweeView view, int resId, int width, int height,
      float[] radii,
      boolean autoPlayAnimations) {
    FrescoUtils.setController(resId, width, height, radii, autoPlayAnimations, view);
  }

  @Override
  public void loadImage(GenericDraweeView view, int resId, int width, int height,
      float[] radii, int borderColor, float borderWidth,
      boolean autoPlayAnimations) {
    FrescoUtils
        .setController(resId, width, height, radii, borderColor, borderWidth, autoPlayAnimations,
            view);
  }

  //</editor-fold >

  //<editor-fold desc="加载网络url">
  @Override
  public void loadImage(GenericDraweeView view, @Nullable CharSequence urlOrPath, int width,
      int height) {
    FrescoUtils.setController(urlOrPath, width, height, view);
  }

  //urlOrPath isCircle
  @Override
  public void loadImage(GenericDraweeView view, @Nullable CharSequence urlOrPath, int width,
      int height,
      boolean isCircle) {
    FrescoUtils.setController(urlOrPath, width, height, isCircle, false, view);
  }


  @Override
  public void loadImage(GenericDraweeView view, @Nullable CharSequence urlOrPath, int width,
      int height,
      boolean isCircle,
      boolean autoPlayAnimations) {
    FrescoUtils.setController(urlOrPath, width, height, isCircle, autoPlayAnimations, view);
  }

  @Override
  public void loadImage(GenericDraweeView view, @Nullable CharSequence urlOrPath, int width,
      int height,
      boolean isCircle, int borderColor, float borderWidth,
      boolean autoPlayAnimations) {
    FrescoUtils.setController(urlOrPath, width, height, isCircle, borderColor, borderWidth,
        autoPlayAnimations, view);
  }

  //urlOrPath radii
  @Override
  public void loadImage(GenericDraweeView view, @Nullable CharSequence urlOrPath, int width,
      int height,
      float[] radii) {
    FrescoUtils.setController(urlOrPath, width, height, radii, false, view);
  }

  @Override
  public void loadImage(GenericDraweeView view, @Nullable CharSequence urlOrPath, int width,
      int height,
      float[] radii,
      boolean autoPlayAnimations) {
    FrescoUtils.setController(urlOrPath, width, height, radii, autoPlayAnimations, view);
  }

  @Override
  public void loadImage(GenericDraweeView view, @Nullable CharSequence urlOrPath, int width,
      int height,
      float[] radii, int borderColor, float borderWidth,
      boolean autoPlayAnimations) {
    FrescoUtils.setController(urlOrPath, width, height, radii, borderColor, borderWidth,
        autoPlayAnimations, view);
  }

  //</editor-fold >
  @Override
  public void clearMemoryCaches() {
    FrescoUtils.clearMemoryCaches();
  }

  @Override
  public void clearDiskCaches() {
    FrescoUtils.clearDiskCaches();
  }

  @Override
  public void clearCaches() {
    FrescoUtils.clearCaches();
  }
}
