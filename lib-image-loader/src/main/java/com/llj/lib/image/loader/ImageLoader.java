package com.llj.lib.image.loader;

import android.content.Context;
import android.support.annotation.ColorInt;

import com.facebook.drawee.view.GenericDraweeView;

/**
 * ArchitectureDemo
 * describe:图片加载器
 * author liulj
 * date 2018/5/4
 */
public class ImageLoader implements IImageLoader<GenericDraweeView> {

    private static IImageLoader sImageLoader;

    private ImageLoader(Context context) {
        FrescoUtils.initFresco(context);
    }

    public static IImageLoader getInstance(Context context) {
        synchronized (ImageLoader.class) {
            if (sImageLoader == null) {
                sImageLoader = new ImageLoader(context);
            }
        }
        return sImageLoader;
    }


    public ImageLoader(Builder builder) {
    }

    @Override
    public void init(Context context) {
        FrescoUtils.initFresco(context);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 加载本地图片
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void loadImage(int resId, int width, int height, GenericDraweeView view) {
        FrescoUtils.setController(resId, width, height, view);
    }

    @Override
    public void loadImage(int resId, int width, int height, boolean isCircle, GenericDraweeView view) {
        FrescoUtils.setController(resId, width, height, isCircle, view);
    }

    @Override
    public void loadImage(int resId, int width, int height, boolean isCircle, int borderColor, float borderWidth, GenericDraweeView view) {
        FrescoUtils.setController(resId, width, height, isCircle, borderColor, borderWidth, view);
    }

    @Override
    public void loadImage(int resId, int width, int height, float[] radii, GenericDraweeView view) {
        FrescoUtils.setController(resId, width, height, radii, view);
    }

    @Override
    public void loadImage(int resId, int width, int height, float[] radii, int borderColor, float borderWidth, GenericDraweeView view) {
        FrescoUtils.setController(resId, width, height, radii, borderColor, borderWidth, view);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 加载网络url
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void loadImage(String urlOrPath, int width, int height, GenericDraweeView view) {
        FrescoUtils.setController(urlOrPath, width, height, view);
    }

    @Override
    public void loadImage(String urlOrPath, int width, int height, boolean isCircle, GenericDraweeView view) {
        FrescoUtils.setController(urlOrPath, width, height, isCircle, view);
    }

    @Override
    public void loadImage(String urlOrPath, int width, int height, boolean isCircle, int borderColor, float borderWidth, GenericDraweeView view) {
        FrescoUtils.setController(urlOrPath, width, height, isCircle, borderColor, borderWidth, view);
    }

    @Override
    public void loadImage(String urlOrPath, int width, int height, float[] radii, GenericDraweeView view) {
        FrescoUtils.setController(urlOrPath, width, height, radii, view);
    }

    @Override
    public void loadImage(String urlOrPath, int width, int height, float[] radii, int borderColor, float borderWidth, GenericDraweeView view) {
        FrescoUtils.setController(urlOrPath, width, height, radii, borderColor, borderWidth, view);
    }

    @Override
    public void clearMemoryCaches() {
        FrescoUtils.clearMemoryCaches();
    }

    @Override
    public void clearDiskCaches() {
        FrescoUtils.clearDiskCaches();
    }

    @Override
    public void clearCaches() {
        FrescoUtils.clearCaches();
    }

    public static class Builder {
        GenericDraweeView view;
        int               resId;
        String            urlOrPath;

        int width;
        int height;

        boolean isCircle;
        float[] radii;

        int   borderColor;
        float borderWidth;

        boolean autoPlayAnimations;

        public Builder(GenericDraweeView view, int resId) {
            this.view = view;
            this.resId = resId;
        }

        public Builder(GenericDraweeView view, String urlOrPath) {
            this.view = view;
            this.urlOrPath = urlOrPath;
        }


        public Builder resize(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder asCircle(@ColorInt int color, float width) {
            isCircle = true;
            return this;
        }

        public Builder setBorder(@ColorInt int color, float width) {
            borderColor = color;
            borderWidth = width;
            return this;
        }

        public Builder setCornersRadii(float[] radii) {
            this.radii = radii;
            return this;
        }


        public ImageLoader build() {
            return new ImageLoader(this);
        }


    }
}
