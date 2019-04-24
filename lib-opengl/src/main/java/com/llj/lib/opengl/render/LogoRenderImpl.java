package com.llj.lib.opengl.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class LogoRenderImpl implements LGLRenderer {

    private Context mContext;

    private float[]     mVertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f,

            0f, 0f,
            0f, 0f,
            0f, 0f,
            0f, 0f
    };
    private FloatBuffer mVertexBuffer;

    private float[]     mFragmentData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };
    private FloatBuffer mFragmentBuffer;

    private int mProgram;
    private int vPosition;
    private int fPosition;
    private int textureId;

    private int mVboId;

    private Bitmap mBitmap;
    private int    mBitmapTextureId;

    public LogoRenderImpl(Context context, int textureId) {
        this.mContext = context;
        this.textureId = textureId;

        mBitmap = ShaderUtil.createTextImage("中国婚博会", 50, "#ff0000", "#00000000", 0);


        float r = 1.0f * mBitmap.getWidth() / mBitmap.getHeight();
        float w = r * 0.1f;

        mVertexData[8] = 0.8f - w;
        mVertexData[9] = -0.8f;

        mVertexData[10] = 0.8f;
        mVertexData[11] = -0.8f;

        mVertexData[12] = 0.8f - w;
        mVertexData[13] = -0.7f;

        mVertexData[14] = 0.8f;
        mVertexData[15] = -0.7f;

        mVertexBuffer = ByteBuffer.allocateDirect(mVertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVertexData);
        mVertexBuffer.position(0);

        mFragmentBuffer = ByteBuffer.allocateDirect(mFragmentData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mFragmentData);
        mFragmentBuffer.position(0);

    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);


        mProgram = createProgram(mContext, R.raw.vs_screen, R.raw.fs_one_texture);

        vPosition = GLES20.glGetAttribLocation(mProgram, "v_Position");
        fPosition = GLES20.glGetAttribLocation(mProgram, "f_Position");

        mVboId = createVbo(mVertexData,mFragmentData,mVertexBuffer,mFragmentBuffer);

        mBitmapTextureId = ShaderUtil.loadBitmapTexture(mBitmap);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1f, 0f, 0f, 1f);

        GLES20.glUseProgram(mProgram);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboId);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8, 0);

        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8, mVertexData.length * 4);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);


        //mBitmap
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mBitmapTextureId);

        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8, 32);

        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8, mVertexData.length * 4);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
