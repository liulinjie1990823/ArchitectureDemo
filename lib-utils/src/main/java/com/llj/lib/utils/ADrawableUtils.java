package com.llj.lib.utils;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/30
 */
public class ADrawableUtils {

    public static GradientDrawable getShapeDrawable(int shape, float radius, @ColorInt int argb) {
        GradientDrawable shapeDrawable = new GradientDrawable();
        shapeDrawable.setShape(shape);
        shapeDrawable.setCornerRadius(radius);
        shapeDrawable.setColor(argb);
        return shapeDrawable;
    }


    public static GradientDrawable getShapeDrawable(int shape, float[] radii, @ColorInt int argb) {
        GradientDrawable shapeDrawable = new GradientDrawable();
        shapeDrawable.setShape(shape);
        shapeDrawable.setCornerRadii(radii);
        shapeDrawable.setColor(argb);
        return shapeDrawable;
    }
}
