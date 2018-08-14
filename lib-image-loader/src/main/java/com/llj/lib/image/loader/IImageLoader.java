package com.llj.lib.image.loader;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/4
 */
public interface IImageLoader<T extends View> {

    void init(Context context);

    void loadImage(@DrawableRes int resId, int width, int height, T view);

    void loadImage(String urlOrPath, int width, int height, T view);

    void clearMemoryCaches();

    void clearDiskCaches();

    void clearCaches();

}
