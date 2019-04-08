package com.llj.lib.opengl.render;

import android.os.SystemClock;
import android.util.Pair;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.llj.lib.opengl.anim.IAnim;
import com.llj.lib.opengl.model.AnimObject;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/8
 */
public class AnimHelper {


    private long mStartTime;

    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    public Pair<Float, Float> calculate(AnimObject animObject) {

        int direction = animObject.direction;
        float maxDistance = animObject.maxDistance;
        long duration = animObject.duration;


        if (mStartTime == 0) {
            mStartTime = SystemClock.uptimeMillis();
        }

        final float input = Math.min((SystemClock.uptimeMillis() - mStartTime) / (duration * 1f), 1.f);
        float mTranslateFactor = (1 - mInterpolator.getInterpolation(input)) * maxDistance;


        if (direction == IAnim.TOP || direction == IAnim.BOTTOM) {
            return new Pair<>(0F, mTranslateFactor);
        } else if (direction == IAnim.LEFT || direction == IAnim.RIGHT) {
            return new Pair<>(mTranslateFactor, 0F);
        }

        return new Pair<>(0F, 0F);
    }

    public void reset() {
        mStartTime = 0;
    }

}
