package com.llj.lib.opengl.render;

import android.opengl.GLES20;
import android.util.Log;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/4
 */
public class FrameBuffer {
    private static final String TAG = FrameBuffer.class.getSimpleName();

    private int mFrameBufferId;
    private int mFboTextureId;


    public void beginDrawToFrameBuffer() {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBufferId);
    }

    public void endDrawToFrameBuffer() {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }

    /**
     * 创建fbo
     *
     * @param sampler
     * @param textureWidth
     * @param textureHeight
     *
     * @return
     */
    public void createFbo(int sampler, int textureWidth, int textureHeight) {
        //创建fbo
        int[] fbo = new int[1];
        GLES20.glGenBuffers(1, fbo, 0);
        mFrameBufferId = fbo[0];
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbo[0]);

        //创建一个纹理
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        mFboTextureId = textureIds[0];
        //绑定纹理
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);

        if (sampler >= 0) {
            GLES20.glUniform1i(sampler, 0);
        }

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        //分配fbo内存,这里的宽高是竖屏的情况下，如果横屏需要重新设置，最好设置成surfaceView的宽高
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, textureWidth, textureHeight, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
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

    }



    public int getFrameBufferId() {
        return mFrameBufferId;
    }

    public int getFboTextureId() {
        return mFboTextureId;
    }
}
