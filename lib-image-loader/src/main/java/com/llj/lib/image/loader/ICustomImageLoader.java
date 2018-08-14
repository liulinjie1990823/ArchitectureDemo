package com.llj.lib.image.loader;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/14
 */
public interface ICustomImageLoader<T extends View> extends IImageLoader<T> {

    void loadImage(@DrawableRes int resId, int width, int height, boolean isCircle, T view);

    void loadImage(@DrawableRes int resId, int width, int height, boolean isCircle, @ColorInt int borderColor, float borderWidth, T view);

    void loadImage(@DrawableRes int resId, int width, int height, float[] radii, T view);

    void loadImage(@DrawableRes int resId, int width, int height, float[] radii, @ColorInt int borderColor, float borderWidth, T view);

    void loadImage(String urlOrPath, int width, int height, boolean isCircle, T view);

    void loadImage(String urlOrPath, int width, int height, boolean isCircle, @ColorInt int borderColor, float borderWidth, T view);

    void loadImage(String urlOrPath, int width, int height, float[] radii, T view);

    void loadImage(String urlOrPath, int width, int height, float[] radii, @ColorInt int borderColor, float borderWidth, T view);
}
