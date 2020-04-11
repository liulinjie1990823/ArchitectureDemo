package com.llj.lib.image.loader.core;

import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * describe:
 *
 * @author liulj
 * @date 2018/5/4
 */
public interface IImageLoader<T extends ImageView> {

  void loadImage(XImageView<T> imageView);

  void loadImage(T view, @DrawableRes int resId, int width, int height);

  void loadImage(T view, @Nullable CharSequence urlOrPath, int width, int height);

  void clearMemoryCaches();

  void clearDiskCaches();

  void clearCaches();

}
