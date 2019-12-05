package com.llj.lib.image.loader;

import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2018/8/14
 */
public interface ICustomImageLoader<T extends View> extends IImageLoader<T> {

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
