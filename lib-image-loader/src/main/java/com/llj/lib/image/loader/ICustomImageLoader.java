package com.llj.lib.image.loader;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/14
 */
public interface ICustomImageLoader<T extends View> extends IImageLoader<T> {

    void loadImage(@DrawableRes int resId, int width, int height,
                   boolean isCircle,
                   boolean autoPlayAnimations,
                   T view);

    void loadImage(@DrawableRes int resId, int width, int height,
                   boolean isCircle, @ColorInt int borderColor, float borderWidth,
                   boolean autoPlayAnimations,
                   T view);

    void loadImage(@DrawableRes int resId, int width, int height,
                   float[] radii,
                   boolean autoPlayAnimations,
                   T view);

    void loadImage(@DrawableRes int resId, int width, int height,
                   float[] radii, @ColorInt int borderColor, float borderWidth,
                   boolean autoPlayAnimations,
                   T view);


    void loadImage(@Nullable CharSequence  urlOrPath, int width, int height,
                   boolean isCircle,
                   boolean autoPlayAnimations,
                   T view);

    void loadImage(@Nullable CharSequence  urlOrPath, int width, int height,
                   boolean isCircle, @ColorInt int borderColor, float borderWidth,
                   boolean autoPlayAnimations,
                   T view);

    void loadImage(@Nullable CharSequence  urlOrPath, int width, int height,
                   float[] radii,
                   boolean autoPlayAnimations,
                   T view);

    void loadImage(@Nullable CharSequence  urlOrPath, int width, int height,
                   float[] radii, @ColorInt int borderColor, float borderWidth,
                   boolean autoPlayAnimations,
                   T view);
}
