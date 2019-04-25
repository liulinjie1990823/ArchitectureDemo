package com.llj.lib.opengl.anim;

import android.opengl.GLES20;

import com.llj.lib.opengl.R;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019-04-25
 */
public class Wipe implements ITransition {

    private int mDirectionRef;
    private int mDirection;

    public Wipe(int direction) {
        mDirection = direction;
    }

    public static Wipe LEFT() {
        return new Wipe(LEFT);
    }

    public static Wipe TOP() {
        return new Wipe(TOP);
    }

    public static Wipe RIGHT() {
        return new Wipe(RIGHT);
    }

    public static Wipe BOTTOM() {
        return new Wipe(BOTTOM);
    }

    @Override
    public int getFsRes() {
        return R.raw.fs_two_texture_wipe;
    }

    @Override
    public void onSurfaceCreated(int program) {
        mDirectionRef = GLES20.glGetUniformLocation(program, DIRECTION);
    }

    @Override
    public void bindProperties() {
        if (mDirectionRef >= 0) {
            GLES20.glUniform1f(mDirectionRef, mDirection);
        }
    }
}
