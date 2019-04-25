package com.llj.lib.opengl.anim;

import android.opengl.GLES20;

import com.llj.lib.opengl.R;

/**
 * ArchitectureDemo.
 * describe:CrossZoom
 * author llj
 * date 2019-04-25
 */
public class CrossZoom implements ITransition {
    private int mFirstRef;

    public static CrossZoom INSTANCE() {
        return new CrossZoom();
    }

    @Override
    public int getFsRes() {
        return R.raw.fs_two_texture_cross_zoom;
    }

    @Override
    public void onSurfaceCreated(int program) {
        mFirstRef = GLES20.glGetUniformLocation(program, "strength");
    }

    @Override
    public void bindProperties() {
        if (mFirstRef >= 0) {
            GLES20.glUniform1f(mFirstRef, 0.4F);
        }
    }
}
