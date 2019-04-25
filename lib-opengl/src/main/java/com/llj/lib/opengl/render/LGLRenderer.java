package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;

import com.llj.lib.opengl.anim.IAnim;
import com.llj.lib.opengl.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/29
 */
public interface LGLRenderer extends GLSurfaceView.Renderer, IAnim {

    int BYTES_PER_FLOAT = 4;//float占用的字节

    String V_POSITION = "v_Position";
    String F_POSITION = "f_Position";
    String U_MATRIX   = "u_Matrix";
    String S_TEXTURE  = "s_Texture";
    String S_TEXTURE0 = "s_Texture0";
    String S_TEXTURE1 = "s_Texture1";
    String S_TEXTURE2 = "s_Texture2";
    String S_TEXTURE3 = "s_Texture3";
    String V_COLOR    = "v_Color";
    String PROGRESS   = "progress";

    /**
     * @param vertexData
     *
     * @return
     */
    default FloatBuffer createBuffer(@NonNull float[] vertexData) {
        return (FloatBuffer) ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData)
                .position(0);
    }

    /**
     * @param context
     * @param vertexId
     * @param fragmentId
     *
     * @return
     */
    default int createProgram(Context context, int vertexId, int fragmentId) {
        String vertexSource = ShaderUtil.getRawResource(context, vertexId);
        String fragmentSource = ShaderUtil.getRawResource(context, fragmentId);

        return ShaderUtil.linkProgram(vertexSource, fragmentSource);
    }

    /**
     * 创建vbo
     *
     * @param vertexData
     * @param fragmentData
     * @param vertexBuffer
     * @param fragmentBuffer
     *
     * @return
     */
    default int createVbo(float[] vertexData, float[] fragmentData, FloatBuffer vertexBuffer, FloatBuffer fragmentBuffer) {
        //创建vbo
        int[] vbo = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);

        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        //分配vbo缓存
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * BYTES_PER_FLOAT + fragmentData.length * BYTES_PER_FLOAT, null, GLES20.GL_STATIC_DRAW);
        //为vbo设置顶点数据,先设置mVertexData后设置mFragmentData
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, vertexData.length * BYTES_PER_FLOAT, vertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, vertexData.length * BYTES_PER_FLOAT, fragmentData.length * BYTES_PER_FLOAT, fragmentBuffer);

        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        return vbo[0];
    }

    default int createVbo(float[] vertexData, FloatBuffer vertexBuffer) {
        //创建vbo
        int[] vbo = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);

        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        //分配vbo缓存
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * BYTES_PER_FLOAT, vertexBuffer, GLES20.GL_STATIC_DRAW);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        return vbo[0];
    }


    default void onClear() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(1f, 0f, 0f, 1f); //设置颜色
    }

    default void unbind() {
    }

}
