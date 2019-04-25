package com.llj.lib.opengl.anim;

import android.opengl.GLES20;

import com.llj.lib.opengl.R;

/**
 * ArchitectureDemo.
 * describe:SimpleZoom
 * author llj
 * date 2019-04-25
 */
public class StereoViewer implements ITransition {
    private int mFirstRef=-1;
    private int mSecondRef=-1;

    public static StereoViewer INSTANCE() {
        return new StereoViewer();
    }

    @Override
    public int getFsRes() {
        return R.raw.fs_two_texture_stereo_viewer;
    }

    @Override
    public void onSurfaceCreated(int program) {
        mFirstRef = GLES20.glGetUniformLocation(program, "zoom");
        mSecondRef = GLES20.glGetUniformLocation(program, "corner_radius");
    }

    @Override
    public void bindProperties() {
        if (mFirstRef >= 0) {
            GLES20.glUniform1f(mFirstRef, 0.88F);
        }
        if (mSecondRef >= 0) {
            GLES20.glUniform1f(mSecondRef, 0.22F);
        }
    }
}
