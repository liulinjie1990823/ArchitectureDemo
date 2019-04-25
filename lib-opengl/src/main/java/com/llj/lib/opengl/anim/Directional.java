package com.llj.lib.opengl.anim;

import android.opengl.GLES20;

import com.llj.lib.opengl.R;

/**
 * ArchitectureDemo.
 * describe:Directional
 * author llj
 * date 2019-04-25
 */
public class Directional implements ITransition {

    private int     mDirectionRef;
    private float[] mDirection;

    public Directional(int direction) {
        if (direction == LEFT) {
            mDirection = new float[]{1, 0};
        } else if (direction == TOP) {
            mDirection = new float[]{0, 1};
        } else if (direction == RIGHT) {
            mDirection = new float[]{-1, 0};
        } else if (direction == BOTTOM) {
            mDirection = new float[]{0, -1};
        }
    }

    public static Directional LEFT() {
        return new Directional(LEFT);
    }

    public static Directional TOP() {
        return new Directional(TOP);
    }

    public static Directional RIGHT() {
        return new Directional(RIGHT);
    }

    public static Directional BOTTOM() {
        return new Directional(BOTTOM);
    }

    @Override
    public int getFsRes() {
        return R.raw.fs_two_texture_directional;
    }

    @Override
    public void onSurfaceCreated(int program) {
        mDirectionRef = GLES20.glGetUniformLocation(program, DIRECTION);
    }

    @Override
    public void bindProperties() {
        if (mDirectionRef >= 0) {
            GLES20.glUniform2f(mDirectionRef, mDirection[0], mDirection[1]);
        }
    }
}
