package com.llj.lib.opengl.model;

import androidx.annotation.DrawableRes;

import com.llj.lib.opengl.anim.IAnim;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/8
 */
public class AnimParam {

    public @IAnim.Direction int   direction;
    public                  float maxDistance;
    public                  float maxScale   = 1F;
    public                  float finalScale = 1F;
    public                  float secondScale;
    public                  long  transDuration;
    public                  long  showDuration;

    public @DrawableRes int resId;


    public AnimParam(int direction,  float maxScale, float finalScale, float secondScaleFactor, long transDuration, long showDuration, int resId) {
        this.direction = direction;
        this.maxScale = maxScale;
        this.secondScale = finalScale * secondScaleFactor;
        this.finalScale = finalScale;
        this.transDuration = transDuration;
        this.showDuration = showDuration;
        this.resId = resId;
    }
}