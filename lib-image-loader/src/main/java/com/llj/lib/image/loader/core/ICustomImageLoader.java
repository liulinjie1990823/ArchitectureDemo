package com.llj.lib.image.loader.core;

import android.content.Context;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2018/8/14
 */
public interface ICustomImageLoader<T extends ImageView> extends IImageLoader<T> {

  default ICustomImageLoader<T> init(Context context) {
    return this;
  }

  void loadImage(T view, @DrawableRes int resId, int width, int height,
      boolean isCircle);

  void loadImage(T view, @DrawableRes int resId, int width, int height,
      boolean isCircle,
      boolean autoPlayAnimations);

  void loadImage(T view, @DrawableRes int resId, int width, int height,
      boolean isCircle, @ColorInt int borderColor, float borderWidth,
      boolean autoPlayAnimations);

  void loadImage(T view, @DrawableRes int resId, int width, int height,
      float[] radii);

  void loadImage(T view, @DrawableRes int resId, int width, int height,
      float[] radii,
      boolean autoPlayAnimations);

  void loadImage(T view, @DrawableRes int resId, int width, int height,
      float[] radii, @ColorInt int borderColor, float borderWidth,
      boolean autoPlayAnimations);


  void loadImage(T view, @Nullable CharSequence urlOrPath, int width, int height,
      boolean isCircle);

  void loadImage(T view, @Nullable CharSequence urlOrPath, int width, int height,
      boolean isCircle,
      boolean autoPlayAnimations);

  void loadImage(T view, @Nullable CharSequence urlOrPath, int width, int height,
      boolean isCircle, @ColorInt int borderColor, float borderWidth,
      boolean autoPlayAnimations);

  void loadImage(T view, @Nullable CharSequence urlOrPath, int width, int height,
      float[] radii);

  void loadImage(T view, @Nullable CharSequence urlOrPath, int width, int height,
      float[] radii,
      boolean autoPlayAnimations);

  void loadImage(T view, @Nullable CharSequence urlOrPath, int width, int height,
      float[] radii, @ColorInt int borderColor, float borderWidth,
      boolean autoPlayAnimations);
}
