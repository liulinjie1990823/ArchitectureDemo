package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.llj.lib.opengl.utils.ShaderUtil;

import java.nio.FloatBuffer;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/29
 */
public abstract class LGLRenderer implements GLSurfaceView.Renderer {
    public static final String TAG = LGLRenderer.class.getSimpleName();

    /**
     * @param context
     * @param vertexId
     * @param fragmentId
     *
     * @return
     */
    protected int createProgram(Context context, int vertexId, int fragmentId) {
        String vertexSource = ShaderUtil.getRawResource(context, vertexId);
        String fragmentSource = ShaderUtil.getRawResource(context, fragmentId);

        return ShaderUtil.createProgram(vertexSource, fragmentSource);
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
    protected int createVbo(float[] vertexData, float[] fragmentData, FloatBuffer vertexBuffer, FloatBuffer fragmentBuffer) {
        //创建vbo
        int[] vbo = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);

        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        //分配vbo缓存
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null, GLES20.GL_STATIC_DRAW);
        //为vbo设置顶点数据,先设置mVertexData后设置mFragmentData
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);

        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        return vbo[0];
    }


    /**
     * @param sampler
     * @param mTextureWidth
     * @param mTextureHeight
     *
     * @return
     */
    protected FboObject createFbo(int sampler, int mTextureWidth, int mTextureHeight) {
        //创建fbo
        int[] fbo = new int[1];
        GLES20.glGenBuffers(1, fbo, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbo[0]);


        //创建一个纹理
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        //绑定纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        if (sampler >= 0) {
            GLES20.glUniform1i(sampler, 0);
        }


        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        //分配fbo内存,这里的宽高是竖屏的情况下，如果横屏需要重新设置，最好设置成surfaceView的宽高
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, mTextureWidth, mTextureHeight, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        //把纹理绑定到fbo
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, textureIds[0], 0);
        //检查fbo是否绑定成功
        if (GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER) != GLES20.GL_FRAMEBUFFER_COMPLETE) {
            Log.e(TAG, "fbo ERROR");
        } else {
            Log.e(TAG, "fbo success");
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);

        return new FboObject(fbo[0], textureIds[0]);
    }

    public static class FboObject {
        public int fboId;
        public int fboTextureId;

        public FboObject(int fboId, int fboTextureId) {
            this.fboId = fboId;
            this.fboTextureId = fboTextureId;
        }
    }

}
