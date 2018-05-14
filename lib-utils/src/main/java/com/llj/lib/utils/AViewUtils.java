package com.llj.lib.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by liulj on 16/4/18.
 */
public class AViewUtils {

    public static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public static boolean isInVisible(View view) {
        return view.getVisibility() == View.INVISIBLE;
    }

    public static boolean isNotVisible(View view) {
        return view.getVisibility() == View.INVISIBLE || view.getVisibility() == View.GONE;
    }

    public static boolean isGone(View view) {
        return view.getVisibility() == View.GONE;
    }


    public static View measureItem(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight,
                    View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
        return view;
    }

    public static void logWidthAndHeight(int width, int height) {
        LogUtil.LLJe(String.format("width:%d;height:%d", width, height));
    }

    public static View measure(View view, int width, int height) {
        int childWidthSpec = makeMeasureSpec(width);
        int childHeightSpec = makeMeasureSpec(height);
        view.measure(childWidthSpec, childHeightSpec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        logWidthAndHeight(view.getMeasuredWidth(), view.getMeasuredHeight());
        return view;
    }

    public static View measureItem(View view, int width) {
        int childWidthSpec = makeMeasureSpec(width);
        int childHeightSpec = makeMeasureSpec(0);
        view.measure(childWidthSpec, childHeightSpec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        logWidthAndHeight(view.getMeasuredWidth(), view.getMeasuredHeight());
        return view;
    }

    public static View measure(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            int childWidthSpec = makeMeasureSpec(layoutParams.width);
            int childHeightSpec = makeMeasureSpec(layoutParams.height);
            view.measure(childWidthSpec, childHeightSpec);
            view.layout(0, 0, layoutParams.width, layoutParams.height);
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            logWidthAndHeight(view.getMeasuredWidth(), view.getMeasuredHeight());
        }
        return view;
    }

    /**
     * @param widthOrHeight
     * @return
     */
    public static int makeMeasureSpec(int widthOrHeight) {
        int childHeightSpec;
        if (widthOrHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(widthOrHeight, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        return childHeightSpec;
    }

    /**
     * 删除监听器
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static final void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            viewTreeObserver.removeGlobalOnLayoutListener(
                    onGlobalLayoutListener);
        } else {
            viewTreeObserver.removeOnGlobalLayoutListener(
                    onGlobalLayoutListener);
        }
    }
}
