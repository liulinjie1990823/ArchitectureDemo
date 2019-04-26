package com.llj.lib.opengl.render;

import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.llj.lib.opengl.anim.IAnim;
import com.llj.lib.opengl.model.AnimParam;
import com.llj.lib.opengl.model.AnimResult;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/8
 */
public class AnimHelper {


    private long mStartTime;

    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    public AnimResult calculate(AnimParam animParam) {

        AnimResult animResult = new AnimResult();

        int direction = animParam.direction;
        float maxDistance = animParam.maxDistance;
        float maxScale = animParam.maxScale;
        long transDuration = animParam.transDuration;
        long showDuration = animParam.showDuration;


        if (mStartTime == 0) {
            mStartTime = SystemClock.uptimeMillis();
        }

        float mTranslateFactor = 0F;
        float mScaleFactor = 0F;
        if (SystemClock.uptimeMillis() - mStartTime < transDuration) {
            //转场 transDuration
            final float input = Math.min((SystemClock.uptimeMillis() - mStartTime) / (transDuration * 1f), 1.f);
            mTranslateFactor = (1 - mInterpolator.getInterpolation(input)) * maxDistance;
            mScaleFactor = (1 - mInterpolator.getInterpolation(input)) * (maxScale - animParam.secondScale) + animParam.secondScale;
        } else {
            //显示 showDuration
            final float input = Math.min((SystemClock.uptimeMillis() - mStartTime - transDuration) / (showDuration * 1f), 1.f);
            mScaleFactor = (1 - mInterpolator.getInterpolation(input)) * (animParam.secondScale - animParam.finalScale) + animParam.finalScale;
        }


        if (direction == IAnim.TOP || direction == IAnim.BOTTOM) {
            animResult.setTranslateY(mTranslateFactor);
            animResult.setScaleX(mScaleFactor);
            animResult.setScaleY(mScaleFactor);
        } else if (direction == IAnim.LEFT || direction == IAnim.RIGHT) {
            animResult.setTranslateX(mTranslateFactor);
            animResult.setScaleX(mScaleFactor);
            animResult.setScaleY(mScaleFactor);
        }

        return animResult;
    }

    public void reset() {
        mStartTime = 0;
    }

}
