package com.llj.lib.opengl.utils;

import android.opengl.GLES20;
import android.support.annotation.NonNull;

import com.llj.lib.opengl.render.LGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019-04-22
 */
public class VertexBufferHelper {
    private boolean mUseVbo = true;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mFragmentBuffer;
    private int         mVboId;

    private int mVPosition;


    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量
    private static final int VERTEX_STRIDE         = COORDINATE_PER_VERTEX * LGLRenderer.BYTES_PER_FLOAT; // 每个顶点的字节数


    private boolean mVsAndFs;

    public VertexBufferHelper(float[] mVertexData, int program, String name) {
        mVertexBuffer = createBuffer(mVertexData);

        //创建vbo
        if (mUseVbo) {
            mVboId = createVbo(mVertexData, mVertexBuffer);
        }

        //或者对应变量
        mVPosition = GLES20.glGetAttribLocation(program, name);
    }


    public void useVbo() {
        //绑定vbo
        if (mUseVbo) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboId);
        }
    }


    public void bindPosition() {
        //可以从mVertexBuffer中拿数据，如果设置为offset则是偏移量，表示从vbo中获取数据
        GLES20.glEnableVertexAttribArray(mVPosition);
        if (mUseVbo) {
            GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, 0);
        } else {
            GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        }

    }


    public FloatBuffer createBuffer(@NonNull float[] vertexData) {
        return (FloatBuffer) ByteBuffer.allocateDirect(vertexData.length * LGLRenderer.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData)
                .position(0);
    }


    public int createVbo(float[] vertexData, FloatBuffer vertexBuffer) {
        //创建vbo
        int[] vbo = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);
        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        //分配vbo缓存
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * LGLRenderer.BYTES_PER_FLOAT, vertexBuffer, GLES20.GL_STATIC_DRAW);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        return vbo[0];
    }

    public int createVbo(float[] vertexData, float[] fragmentData, FloatBuffer vertexBuffer, FloatBuffer fragmentBuffer) {
        //创建vbo
        int[] vbo = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);

        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        //分配vbo缓存
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * LGLRenderer.BYTES_PER_FLOAT + fragmentData.length * LGLRenderer.BYTES_PER_FLOAT, null, GLES20.GL_STATIC_DRAW);
        //为vbo设置顶点数据,先设置mVertexData后设置mFragmentData
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, vertexData.length * LGLRenderer.BYTES_PER_FLOAT, vertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, vertexData.length * LGLRenderer.BYTES_PER_FLOAT, fragmentData.length * LGLRenderer.BYTES_PER_FLOAT, fragmentBuffer);

        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        return vbo[0];
    }

    public void unbind(){
        //
        GLES20.glDisableVertexAttribArray(mVPosition);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
