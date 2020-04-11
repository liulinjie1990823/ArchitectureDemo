package com.llj.lib.image.loader;

import android.util.SparseArray;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.llj.lib.image.loader.core.ICustomImageLoader;
import com.llj.lib.image.loader.core.ImageConfig;
import com.llj.lib.image.loader.core.XImageView;

/**
 * describe 图片加载工具类
 *
 * @author liulinjie
 * @date 2020/4/11 9:26 PM
 */
public class ImageLoader implements ICustomImageLoader<ImageView> {

  private static SparseArray<ICustomImageLoader<ImageView>> mEngines = new SparseArray<>();

  private static ImageLoader                   sImageLoader;
  private static ICustomImageLoader<ImageView> sEngine;

  public static ImageLoader getInstance() {
    if (mEngines.size() == 0) {
      throw new RuntimeException("no mEngines");
    }
    sEngine = mEngines.get(ImageConfig.DEFAULT_ENGINE);
    if (sEngine == null) {
      throw new RuntimeException("the type of engine is null");
    }
    synchronized (ImageLoader.class) {
      if (sImageLoader == null) {
        sImageLoader = new ImageLoader();
      }
    }
    return sImageLoader;
  }

  public static ImageLoader getInstance(int type) {
    if (mEngines.size() == 0) {
      throw new RuntimeException("no mEngines");
    }
    sEngine = mEngines.get(type);
    if (sEngine == null) {
      throw new RuntimeException("the type of engine is null");
    }
    synchronized (ImageLoader.class) {
      if (sImageLoader == null) {
        sImageLoader = new ImageLoader();
      }
    }
    return sImageLoader;
  }


  public static void addImageLoadEngine(int type,
      ICustomImageLoader engine) {
    int indexOfKey = mEngines.indexOfKey(type);
    if (indexOfKey >= 0) {
      throw new RuntimeException("the same engine exist");
    }
    mEngines.put(type, engine);
  }

  @Override
  public void loadImage(XImageView<ImageView> imageView) {

  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height) {
    sEngine.loadImage(view, resId, width, height);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, boolean isCircle) {
    sEngine.loadImage(view, resId, width, height, isCircle);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, boolean isCircle,
      boolean autoPlayAnimations) {
    sEngine.loadImage(view, resId, width, height, isCircle, autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, boolean isCircle,
      int borderColor, float borderWidth, boolean autoPlayAnimations) {
    sEngine.loadImage(view, resId, width, height, isCircle, borderColor, borderWidth,
        autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, float[] radii) {
    sEngine.loadImage(view, resId, width, height, radii);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, float[] radii,
      boolean autoPlayAnimations) {
    sEngine.loadImage(view, resId, width, height, radii, autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, float[] radii,
      int borderColor, float borderWidth, boolean autoPlayAnimations) {
    sEngine
        .loadImage(view, resId, width, height, radii, borderColor, borderWidth, autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height) {
    sEngine.loadImage(view, urlOrPath, width, height);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      boolean isCircle) {
    sEngine.loadImage(view, urlOrPath, width, height, isCircle);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      boolean isCircle, boolean autoPlayAnimations) {
    sEngine.loadImage(view, urlOrPath, width, height, isCircle, autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      boolean isCircle, int borderColor, float borderWidth, boolean autoPlayAnimations) {
    sEngine.loadImage(view, urlOrPath, width, height, isCircle, borderColor, borderWidth,
        autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      float[] radii) {
    sEngine.loadImage(view, urlOrPath, width, height, radii);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      float[] radii, boolean autoPlayAnimations) {
    sEngine.loadImage(view, urlOrPath, width, height, radii, autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      float[] radii, int borderColor, float borderWidth, boolean autoPlayAnimations) {
    sEngine.loadImage(view, urlOrPath, width, height, radii, borderColor, borderWidth,
        autoPlayAnimations);
  }

  @Override
  public void clearMemoryCaches() {
    sEngine.clearMemoryCaches();
  }

  @Override
  public void clearDiskCaches() {
    sEngine.clearDiskCaches();
  }

  @Override
  public void clearCaches() {
    sEngine.clearCaches();
  }
}
