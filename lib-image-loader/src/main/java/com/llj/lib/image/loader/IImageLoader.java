package com.llj.lib.image.loader;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;

import okhttp3.OkHttpClient;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/4
 */
public interface IImageLoader<T extends View> {

    void init(Context context);

    void init(Context context, OkHttpClient okHttpClient);

    void loadImage(@DrawableRes int resId, int width, int height, T view);

    void loadImage(@Nullable CharSequence  urlOrPath, int width, int height, T view);

    void clearMemoryCaches();

    void clearDiskCaches();

    void clearCaches();

}
