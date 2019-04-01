package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;
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
public class ShapeRender implements GLSurfaceView.Renderer {
    private Context  mContext;
    private Triangle mTriangle;

    public ShapeRender(Context context) {
        mContext = context;
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        mTriangle = new Triangle(mContext);
    }

    public void onDrawFrame(GL10 unused) {
        mTriangle.draw();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
}
