package com.llj.widget.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.MyBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.llj.widget.ui.widget.CircleView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/11
 */
@Route(path = CRouter.CIRCLE_VIEW_ACTIVITY)
public class CircleViewActivity extends MyBaseActivity {
    @BindView(R.id.iv_top_bag)        CircleView mIvTopBag;
    @BindView(R.id.iv_top_bag_sticky) ImageView  mIvTopBagSticky;
ArrayList
    @Override
    public int layoutId() {
        return R.layout.activity_circle_view;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        String beginColor = "#004A98";
        String endColor = "#458BD5";
        mIvTopBag.setRectF(0f, 0f, 1080, (1080) * 440 / 750f * 2, 100f, 0f);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mIvTopBag.getLayoutParams();
        layoutParams.topMargin = (int) -(mIvTopBag.getRectF().bottom / 2);

        mIvTopBag.setColor(beginColor, endColor);


        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT
                , new int[]{Color.parseColor(beginColor), Color.parseColor(endColor)});
        mIvTopBagSticky.setImageDrawable(gradientDrawable);

    }

    @Override
    public void initData() {

    }

}
