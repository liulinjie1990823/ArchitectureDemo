package com.llj.lib.opengl.anim;

import android.support.annotation.RawRes;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.llj.lib.opengl.R;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019-04-25
 */
public interface ITransition {

    int LEFT   = 0;
    int TOP    = 1;
    int RIGHT  = 2;
    int BOTTOM = 3;

    String DIRECTION = "direction";


    default long getTransDuration() {
        return 3000;
    }

    default long getShowDuration() {
        return 1000;
    }

    default Interpolator getInterpolator() {
        return new AccelerateDecelerateInterpolator();
    }

    @RawRes
    default int getVsRes() {
        return R.raw.vs_screen_matrix;
    }

    @RawRes
    int getFsRes();

    default int getTextureSize() {
        return 2;
    }

    void onSurfaceCreated(int program);

    void bindProperties();
}
