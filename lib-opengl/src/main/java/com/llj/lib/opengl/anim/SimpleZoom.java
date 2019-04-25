package com.llj.lib.opengl.anim;

import android.opengl.GLES20;

import com.llj.lib.opengl.R;

/**
 * ArchitectureDemo.
 * describe:SimpleZoom
 * author llj
 * date 2019-04-25
 */
public class SimpleZoom implements ITransition {
    private int mFirstRef;

    public static SimpleZoom INSTANCE() {
        return new SimpleZoom();
    }

    @Override
    public int getFsRes() {
        return R.raw.fs_two_texture_simple_zoom;
    }

    @Override
    public void onSurfaceCreated(int program) {
        mFirstRef = GLES20.glGetUniformLocation(program, "zoom_quickness");
    }

    @Override
    public void bindProperties() {
        if (mFirstRef >= 0) {
            GLES20.glUniform1f(mFirstRef, 0.3F);
        }
    }
}
