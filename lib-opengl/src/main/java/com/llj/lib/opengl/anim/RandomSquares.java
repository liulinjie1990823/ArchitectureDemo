package com.llj.lib.opengl.anim;

import android.opengl.GLES20;

import com.llj.lib.opengl.R;

/**
 * ArchitectureDemo.
 * describe:RandomSquares
 * author llj
 * date 2019-04-25
 */
public class RandomSquares implements ITransition {
    private int mFirstRef  = -1;
    private int mSecondRef = -1;

    private int[] size       = {10, 10};
    private float smoothness = 0.5F;

    public static RandomSquares INSTANCE() {
        return new RandomSquares();
    }

    @Override
    public int getFsRes() {
        return R.raw.fs_two_texture_random_squares;
    }

    @Override
    public void onSurfaceCreated(int program) {
        mFirstRef = GLES20.glGetUniformLocation(program, "size");
        mSecondRef = GLES20.glGetUniformLocation(program, "smoothness");
    }

    @Override
    public void bindProperties() {
        if (mFirstRef >= 0) {
            GLES20.glUniform2iv(mFirstRef, 1, size, 0);
        }
        if (mSecondRef >= 0) {
            GLES20.glUniform1f(mSecondRef, smoothness);
        }
    }
}
