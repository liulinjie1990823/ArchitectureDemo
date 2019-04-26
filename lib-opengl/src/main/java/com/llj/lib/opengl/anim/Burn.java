package com.llj.lib.opengl.anim;

import android.opengl.GLES20;

import com.llj.lib.opengl.R;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019-04-25
 */
public class Burn implements ITransition {
    private int mFirstRef;

    private float[] color = {0.9f, 0.4f, 0.2f};

    public static Burn INSTANCE() {
        return new Burn();
    }

    @Override
    public int getFsRes() {
        return R.raw.fs_two_texture_burn;
    }

    @Override
    public void onSurfaceCreated(int program) {
        mFirstRef = GLES20.glGetUniformLocation(program, "color");
    }

    @Override
    public void bindProperties() {
        if (mFirstRef >= 0) {
            GLES20.glUniform1fv(mFirstRef, 3, color, 0);
        }
    }
}
