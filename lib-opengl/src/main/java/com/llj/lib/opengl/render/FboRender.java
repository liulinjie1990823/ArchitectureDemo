package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;
import android.support.annotation.DrawableRes;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/1
 */
public class FboRender {
    public static final String TAG = FboRender.class.getSimpleName();

    private              Context mContext;
    private @DrawableRes int     mResId;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mFragmentBuffer;

    private int mVPosition;//顶点位置
    private int mFPosition;//片元位置
    private int mProgram;//gl程序
    private int mSampler;


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
    private final int vertexStride = COORDINATE_PER_VERTEX * 4; // 4 bytes per vertex


    public FboRender(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        mVertexBuffer = (FloatBuffer) ByteBuffer.allocateDirect(mVertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVertexData)
                .position(0);


        mFragmentBuffer = (FloatBuffer) ByteBuffer.allocateDirect(mFragmentData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mFragmentData)
                .position(0);
    }

    private int createVbo() {
        //创建vbo
        int[] vbos = new int[1];
        GLES20.glGenBuffers(1, vbos, 0);

        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbos[0]);
        //分配vbo缓存
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mVertexData.length * 4 + mFragmentData.length * 4, null, GLES20.GL_STATIC_DRAW);
        //为vbo设置顶点数据,先设置mVertexData后设置mFragmentData
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, mVertexData.length * 4, mVertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, mVertexData.length * 4, mFragmentData.length * 4, mFragmentBuffer);

        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        return vbos[0];
    }


    public void onSurfaceChanged(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }


    public void onSurfaceCreated() {
        String vertexSource = ShaderUtil.getRawResource(mContext, R.raw.vertex_shader_screen);
        String fragmentSource = ShaderUtil.getRawResource(mContext, R.raw.fragment_shader_screen);

        mProgram = ShaderUtil.createProgram(vertexSource, fragmentSource);

        mVPosition = GLES20.glGetAttribLocation(mProgram, "v_Position");
        mFPosition = GLES20.glGetAttribLocation(mProgram, "f_Position");
        mSampler = GLES20.glGetUniformLocation(mProgram, "sTexture");

        mVboId = createVbo();
    }


    public void onDrawFrame(int textureId) {

        //清屏
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //设置颜色
        GLES20.glClearColor(1f, 0f, 0f, 1f);

        GLES20.glUseProgram(mProgram);

        //绑定纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboId);

        GLES20.glEnableVertexAttribArray(mVPosition);
        //可以从mVertexBuffer中拿数据，如果设置为offset则是偏移量，表示从vbo中获取数据
        GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);

        GLES20.glEnableVertexAttribArray(mFPosition);
        GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mVertexData.length * 4);

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
