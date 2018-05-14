package com.llj.lib.image.loader;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/4
 */
public interface ImageLoaderInterface<T extends ImageView> {

    void init(Context context);

    void loadImage(@DrawableRes int resId, int width, int height, T view);

    void loadImage(@DrawableRes int resId, int width, int height, boolean isCircle, T view);

    void loadImage(@DrawableRes int resId, int width, int height, boolean isCircle, @ColorInt int borderColor, float borderWidth, T view);

    void loadImage(@DrawableRes int resId, int width, int height, float[] radii, T view);

    void loadImage(@DrawableRes int resId, int width, int height, float[] radii, @ColorInt int borderColor, float borderWidth, T view);

    void loadImage(String urlOrPath, int width, int height, T view);

    void loadImage(String urlOrPath, int width, int height, boolean isCircle, T view);

    void loadImage(String urlOrPath, int width, int height, boolean isCircle, @ColorInt int borderColor, float borderWidth, T view);

    void loadImage(String urlOrPath, int width, int height, float[] radii, T view);

    void loadImage(String urlOrPath, int width, int height, float[] radii, @ColorInt int borderColor, float borderWidth, T view);

    void clearMemoryCaches();

    void clearDiskCaches();

    void clearCaches();

}
