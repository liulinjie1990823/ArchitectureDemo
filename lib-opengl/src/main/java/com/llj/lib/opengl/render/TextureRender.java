package com.llj.lib.opengl.render;

import android.content.Context;
import android.support.annotation.DrawableRes;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/29
 */
public class TextureRender implements LGLRenderer {
    private BitmapRenderImpl mBitmapRenderImpl;


    public TextureRender(Context context, @DrawableRes int resId) {
        mBitmapRenderImpl = new BitmapRenderImpl(context, resId);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mBitmapRenderImpl.onSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mBitmapRenderImpl.onSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mBitmapRenderImpl.onDrawFrame();
    }
}
