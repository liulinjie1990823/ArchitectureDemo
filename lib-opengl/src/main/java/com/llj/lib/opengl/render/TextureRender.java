package com.llj.lib.opengl.render;

import android.content.Context;
import android.support.annotation.DrawableRes;

import com.llj.lib.opengl.shape.GLBitmap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/29
 */
public class TextureRender implements LGLRenderer {
    private GLBitmap mGLBitmap;


    public TextureRender(Context context, @DrawableRes int resId) {
        mGLBitmap = new GLBitmap(context, resId);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mGLBitmap.onSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mGLBitmap.onSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mGLBitmap.onDrawFrame();
    }
}
