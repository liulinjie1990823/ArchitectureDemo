package com.llj.lib.opengl.anim;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/8
 */
public interface IAnim {

    public static final int TOP    = 0;//动画方向
    public static final int BOTTOM = 1;
    public static final int LEFT   = 2;
    public static final int RIGHT  = 3;

    @IntDef({TOP, BOTTOM, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Direction {
    }
}
