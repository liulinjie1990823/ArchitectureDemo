package com.llj.lib.opengl.utils;

import android.content.Context;
import android.opengl.GLES20;
import android.os.SystemClock;
import android.view.animation.Interpolator;

import com.llj.lib.opengl.anim.ITransition;
import com.llj.lib.opengl.render.LGLRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019-04-23
 */
public class TextureHelper<T extends ITransition> {

    private int mProgram;//gl程序
    private int mProgressRef;//时间参数

    private List<Integer> mTextureRefList = new ArrayList<>();

    private float mTimePlus;

    private T mTransition;

    private long         mTransDuration;
    private long         mShowDuration;
    private Interpolator mInterpolator;

    public TextureHelper(Context context, T transition) {
        mTransition = transition;
        mTransDuration = transition.getTransDuration();
        mShowDuration = transition.getShowDuration();
        mInterpolator = transition.getInterpolator();


        mProgram = createProgram(context, transition.getVsRes(), transition.getFsRes());

        mTextureRefList.clear();
        //创建textureData
        for (int i = 0; i < transition.getTextureSize(); i++) {
            mTextureRefList.add(GLES20.glGetUniformLocation(mProgram, LGLRenderer.S_TEXTURE + i));
        }
        //创建时间ref
        mProgressRef = GLES20.glGetUniformLocation(mProgram, LGLRenderer.PROGRESS);

        transition.onSurfaceCreated(mProgram);

    }

    public int getProgram() {
        return mProgram;
    }

    public List<Integer> getTextureList() {
        return mTextureRefList;
    }


    private int createProgram(Context context, int vertexId, int fragmentId) {
        String vertexSource = ShaderUtil.getRawResource(context, vertexId);
        String fragmentSource = ShaderUtil.getRawResource(context, fragmentId);

        return ShaderUtil.linkProgram(vertexSource, fragmentSource);
    }

    public void bindTexture(int imgTextureId, int index) {
        if (mTextureRefList.size() > index) {
            Integer textureData = mTextureRefList.get(index);
            if (textureData >= 0) {
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + index);//设置纹理可用
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imgTextureId);//将已经处理好的纹理绑定到gl上
                GLES20.glUniform1i(textureData, index);//将第x个纹理设置到fragment_shader中进一步处理
            }
        }
    }

    private long  mStartTime;
    private float mProgress;
    private int   mIndex;

    public int getIndex() {
        return mIndex;
    }

    public int bindProgress() {
        if (mStartTime == 0) {
            mStartTime = SystemClock.uptimeMillis();
        }

        if (SystemClock.uptimeMillis() - mStartTime < mTransDuration) {
            //转场持续mTransDuration
            //input=0->1
            final float input = Math.min((SystemClock.uptimeMillis() - mStartTime) / (mTransDuration * 1f), 1.f);
            mProgress = mInterpolator.getInterpolation(input);
        } else if (SystemClock.uptimeMillis() - mStartTime - mTransDuration < mShowDuration) {
            //显示持续mShowDuration
            final float input = Math.min((SystemClock.uptimeMillis() - mStartTime - mTransDuration) / (mShowDuration * 1f), 1.f);
        } else {
            //切换到第二个场景
            mStartTime = 0;
            mProgress = 0F;
            mIndex++;
        }
        if (mProgressRef >= 0) {
            GLES20.glUniform1f(mProgressRef, mProgress);
        }
        return mIndex;
    }

    public void bindProperties() {
        mTransition.bindProperties();
    }

    public void unbind() {
        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

}
