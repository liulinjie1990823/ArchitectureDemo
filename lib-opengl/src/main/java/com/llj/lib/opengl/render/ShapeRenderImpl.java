package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.llj.lib.opengl.shape.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/1
 */
public class ShapeRenderImpl implements GLSurfaceView.Renderer {
    private Context  mContext;
    private Triangle mTriangle;

    public ShapeRenderImpl(Context context) {
        mContext = context;
        mTriangle = new Triangle(mContext);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        mTriangle.onSurfaceCreated(unused, config);
    }

    public void onDrawFrame(GL10 unused) {
        mTriangle.onDrawFrame(unused);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        mTriangle.onSurfaceChanged(unused, width, height);
    }
}
