package com.llj.lib.opengl.model;

import com.llj.lib.opengl.anim.IAnim;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/8
 */
public class AnimObject {

    public @IAnim.Direction int   direction;
    public                  float maxDistance;
    public                  long  duration;

    public AnimObject(@IAnim.Direction int direction, float maxDistance, long duration) {
        this.direction = direction;
        this.maxDistance = maxDistance;
        this.duration = duration;
    }
}