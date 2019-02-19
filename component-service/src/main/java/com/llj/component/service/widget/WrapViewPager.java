package com.llj.component.service.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/2/19
 */
public class WrapViewPager extends NoScrollViewPager {
    public WrapViewPager(Context context) {
        super(context);
    }

    public WrapViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }

        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height,
                View.MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
