package com.llj.lib.image.loader;

import android.content.Context;

import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/14
 */
public class FrescoImageLoader implements ICustomImageLoader<GenericDraweeView> {

    private static ICustomImageLoader<GenericDraweeView> sImageLoader;

    private FrescoImageLoader(Context context) {
        init(context);
    }

    private FrescoImageLoader(Context context, OkHttpClient okHttpClient) {
        init(context, okHttpClient);
    }

    public static ICustomImageLoader<GenericDraweeView> getInstance(Context context) {
        synchronized (FrescoImageLoader.class) {
            if (sImageLoader == null) {
                sImageLoader = new FrescoImageLoader(context);
            }
        }
        return sImageLoader;
    }

    public static ICustomImageLoader<GenericDraweeView> getInstance(Context context, OkHttpClient okHttpClient) {
        synchronized (FrescoImageLoader.class) {
            if (sImageLoader == null) {
                sImageLoader = new FrescoImageLoader(context, okHttpClient);
            }
        }
        return sImageLoader;
    }


    @Override
    public void init(Context context) {
        //网络请求，使用OkHttpClient会经过代理
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(15000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(15000, TimeUnit.MILLISECONDS);
        builder.readTimeout(15000, TimeUnit.MILLISECONDS);
        builder.cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context.getApplicationContext())));

        FrescoUtils.initFresco(context);
    }

    @Override
    public void init(Context context, OkHttpClient okHttpClient) {
        if (okHttpClient != null) {
            FrescoUtils.initFresco(context, new OkHttpNetworkFetcher(okHttpClient));
        } else {
            FrescoUtils.initFresco(context, null);
        }

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
}
