package com.llj.lib.opengl.anim;

import android.opengl.GLES20;

import com.llj.lib.opengl.R;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019-04-25
 */
public class Swap implements ITransition {
    private int mFirstRef  = -1;
    private int mSecondRef = -1;
    private int mThirdRef  = -1;

    private float reflection  = 0.4f; // = 0.4
    private float perspective = 0.2f; // = 0.2
    private float depth       = 3.0f; // = 3.0

    public static Swap INSTANCE() {
        return new Swap();
    }

    @Override
    public int getFsRes() {
        return R.raw.fs_two_texture_swap;
    }

    @Override
    public void onSurfaceCreated(int program) {
        mFirstRef = GLES20.glGetUniformLocation(program, "reflection");
        mSecondRef = GLES20.glGetUniformLocation(program, "perspective");
        mThirdRef = GLES20.glGetUniformLocation(program, "depth");
    }

    @Override
    public void bindProperties() {
        if (mFirstRef >= 0) {
            GLES20.glUniform1f(mFirstRef, reflection);
        }
        if (mSecondRef >= 0) {
            GLES20.glUniform1f(mSecondRef, perspective);
        }
        if (mThirdRef >= 0) {
            GLES20.glUniform1f(mThirdRef, depth);
        }
    }
}

