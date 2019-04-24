package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;

import com.llj.lib.opengl.shape.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/1
 */
public class ShapeRenderImpl implements LGLRenderer {
    private Context  mContext;
    private Triangle mTriangle;

    public ShapeRenderImpl(Context context) {
        mContext = context;
        mTriangle = new Triangle(mContext);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mTriangle.onSurfaceCreated(gl, config);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mTriangle.onSurfaceChanged(gl, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        onClear();
        mTriangle.onDrawFrame(gl);
        mTriangle.unbind();
    }
}
