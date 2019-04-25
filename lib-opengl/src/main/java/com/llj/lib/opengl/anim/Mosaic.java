package com.llj.lib.opengl.anim;

import android.opengl.GLES20;

import com.llj.lib.opengl.R;

/**
 * ArchitectureDemo.
 * describe:SimpleZoom
 * author llj
 * date 2019-04-25
 */
public class Mosaic implements ITransition {
    private int mFirstRef  = -1;
    private int mSecondRef = -1;

    private int endx = 2; // = 2
    private int endy = -1; // = -1

    public static Mosaic INSTANCE() {
        return new Mosaic();
    }

    @Override
    public int getFsRes() {
        return R.raw.fs_two_texture_mosaic;
    }

    @Override
    public void onSurfaceCreated(int program) {
        mFirstRef = GLES20.glGetUniformLocation(program, "endx");
        mSecondRef = GLES20.glGetUniformLocation(program, "endy");
    }

    @Override
    public void bindProperties() {
        if (mFirstRef >= 0) {
            GLES20.glUniform1i(mFirstRef, endx);
        }
        if (mSecondRef >= 0) {
            GLES20.glUniform1i(mSecondRef, endy);
        }
    }
}
