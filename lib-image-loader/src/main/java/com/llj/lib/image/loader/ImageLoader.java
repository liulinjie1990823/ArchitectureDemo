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

  private static SparseArray<ImageLoader>      sEngines = new SparseArray<>();
  private        ICustomImageLoader<ImageView> mEngine;

  public ImageLoader(ICustomImageLoader<ImageView> engine) {
    mEngine = engine;
  }

  public static ImageLoader getInstance() {
    if (sEngines.size() == 0) {
      throw new RuntimeException("no mEngines");
    }
    ImageLoader engine = sEngines.get(ImageConfig.DEFAULT_ENGINE);
    if (engine == null) {
      throw new RuntimeException("the type of engine is null");
    }
    return engine;
  }

  public static ImageLoader getInstance(int type) {
    if (sEngines.size() == 0) {
      throw new RuntimeException("no mEngines");
    }
    ImageLoader engine = sEngines.get(type);
    if (engine == null) {
      throw new RuntimeException("the type of engine is null");
    }
    return engine;
  }


  public static void addImageLoadEngine(int type,
      ICustomImageLoader engine) {
    int indexOfKey = sEngines.indexOfKey(type);
    if (indexOfKey >= 0) {
      throw new RuntimeException("the same engine exist");
    }
    sEngines.put(type, new ImageLoader(engine));
  }

  @Override
  public void loadImage(XImageView<ImageView> imageView) {

  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height) {
    mEngine.loadImage(view, resId, width, height);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, boolean isCircle) {
    mEngine.loadImage(view, resId, width, height, isCircle);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, boolean isCircle,
      boolean autoPlayAnimations) {
    mEngine.loadImage(view, resId, width, height, isCircle, autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, boolean isCircle,
      int borderColor, float borderWidth, boolean autoPlayAnimations) {
    mEngine.loadImage(view, resId, width, height, isCircle, borderColor, borderWidth,
        autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, float[] radii) {
    mEngine.loadImage(view, resId, width, height, radii);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, float[] radii,
      boolean autoPlayAnimations) {
    mEngine.loadImage(view, resId, width, height, radii, autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, int resId, int width, int height, float[] radii,
      int borderColor, float borderWidth, boolean autoPlayAnimations) {
    mEngine
        .loadImage(view, resId, width, height, radii, borderColor, borderWidth, autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height) {
    mEngine.loadImage(view, urlOrPath, width, height);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      boolean isCircle) {
    mEngine.loadImage(view, urlOrPath, width, height, isCircle);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      boolean isCircle, boolean autoPlayAnimations) {
    mEngine.loadImage(view, urlOrPath, width, height, isCircle, autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      boolean isCircle, int borderColor, float borderWidth, boolean autoPlayAnimations) {
    mEngine.loadImage(view, urlOrPath, width, height, isCircle, borderColor, borderWidth,
        autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      float[] radii) {
    mEngine.loadImage(view, urlOrPath, width, height, radii);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      float[] radii, boolean autoPlayAnimations) {
    mEngine.loadImage(view, urlOrPath, width, height, radii, autoPlayAnimations);
  }

  @Override
  public void loadImage(ImageView view, @Nullable CharSequence urlOrPath, int width, int height,
      float[] radii, int borderColor, float borderWidth, boolean autoPlayAnimations) {
    mEngine.loadImage(view, urlOrPath, width, height, radii, borderColor, borderWidth,
        autoPlayAnimations);
  }

  @Override
  public void clearMemoryCaches() {
    mEngine.clearMemoryCaches();
  }

  @Override
  public void clearDiskCaches() {
    mEngine.clearDiskCaches();
  }

  @Override
  public void clearCaches() {
    mEngine.clearCaches();
  }
}
