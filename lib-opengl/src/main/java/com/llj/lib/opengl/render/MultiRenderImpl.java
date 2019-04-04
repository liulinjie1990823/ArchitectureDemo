package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.utils.ShaderUtil;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/2
 */
public class MultiRenderImpl extends LGLRenderer {

    public static final String TAG = MultiRenderImpl.class.getSimpleName();

    private Context mContext;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mFragmentBuffer;

    private int mVPosition;//顶点位置
    private int mFPosition;//片元位置
    private int mProgram;//gl程序


    private float[] mVertexData = {
            -1f, -1f,//bottom left
            1f, -1f,//bottom right
            -1f, 1f,//top left
            1f, 1f//top right
    };

    private float[] mFragmentData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };


    private int mVboId;

    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量

    private final int vertexCount  = mVertexData.length / COORDINATE_PER_VERTEX;//需要绘制几个顶点
    private final int vertexStride = COORDINATE_PER_VERTEX * BYTES_PER_FLOAT; // 4 bytes per vertex


    private int    mTextureId;
    private String mFragmentSource;

    public void setTextureId(int textureId) {
        mTextureId = textureId;
    }

    public void setFragmentSource(String fragmentSource) {
        mFragmentSource = fragmentSource;
    }

    public MultiRenderImpl(Context context) {
        mContext = context;
        init();
    }

    @Override
    public void init() {
        mVertexBuffer = createBuffer(mVertexData);
        mFragmentBuffer = createBuffer(mFragmentData);
    }



    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String vertexSource = ShaderUtil.getRawResource(mContext, R.raw.vertex_shader_screen);
        mProgram = ShaderUtil.linkProgram(vertexSource, mFragmentSource);

        mVPosition = GLES20.glGetAttribLocation(mProgram, "v_Position");
        mFPosition = GLES20.glGetAttribLocation(mProgram, "f_Position");

        mVboId = createVbo(mVertexData, mFragmentData, mVertexBuffer, mFragmentBuffer);
    }


    @Override
    public void onDrawFrame(GL10 gl) {

        //清屏
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //设置颜色
        GLES20.glClearColor(1f, 0f, 0f, 1f);

        GLES20.glUseProgram(mProgram);

        //绑定纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboId);

        GLES20.glEnableVertexAttribArray(mVPosition);
        //可以从mVertexBuffer中拿数据，如果设置为offset则是偏移量，表示从vbo中获取数据
        GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);

        GLES20.glEnableVertexAttribArray(mFPosition);
        GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mVertexData.length * 4);

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);

        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

}
