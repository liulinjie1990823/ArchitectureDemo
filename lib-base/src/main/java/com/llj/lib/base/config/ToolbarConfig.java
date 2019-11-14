package com.llj.lib.base.config;

import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import android.util.TypedValue;
import android.view.View;

import com.llj.lib.base.R;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-19
 */
public class ToolbarConfig {

    //dp
    private int      mToolbarHeight;
    private int      mToolbarBackgroundColorRes = -1;
    private Drawable mToolbarBackgroundDrawable;

    //px
    private int mDivideHeight             = 1;
    private int mDivideBackgroundColorRes = -1;
    private int mVisibility               = View.VISIBLE;

    private @DrawableRes int mLeftImageRes = -1;

    private int mTitleUnit     = TypedValue.COMPLEX_UNIT_DIP;
    private int mTitleSize     = 18;
    private int mTitleColorRes = R.color.black;

    private @DrawableRes int mRightImageRes;

    private int mRightUnit         = TypedValue.COMPLEX_UNIT_DIP;
    private int mRightTextSize     = 16;
    private int mRightTextColorRes = R.color.black;


    public int getToolbarHeight() {
        return mToolbarHeight;
    }

    public void setToolbarHeight(int toolbarHeight) {
        mToolbarHeight = toolbarHeight;
    }

    public int getToolbarBackgroundColorRes() {
        return mToolbarBackgroundColorRes;
    }

    public void setToolbarBackgroundColorRes(int toolbarBackgroundColorRes) {
        mToolbarBackgroundColorRes = toolbarBackgroundColorRes;
    }

    public Drawable getToolbarBackgroundDrawable() {
        return mToolbarBackgroundDrawable;
    }

    public void setToolbarBackgroundDrawable(Drawable toolbarBackgroundDrawable) {
        mToolbarBackgroundDrawable = toolbarBackgroundDrawable;
    }

    public int getDivideHeight() {
        return mDivideHeight;
    }

    public void setDivideHeight(int divideHeight) {
        mDivideHeight = divideHeight;
    }

    public int getDivideBackgroundColorRes() {
        return mDivideBackgroundColorRes;
    }

    public void setDivideBackgroundColorRes(int divideBackgroundColorRes) {
        mDivideBackgroundColorRes = divideBackgroundColorRes;
    }

    public int getVisibility() {
        return mVisibility;
    }

    public void setVisibility(int visibility) {
        mVisibility = visibility;
    }

    public int getLeftImageRes() {
        return mLeftImageRes;
    }

    public void setLeftImageRes(int leftImageRes) {
        mLeftImageRes = leftImageRes;
    }

    public int getTitleSize() {
        return mTitleSize;
    }

    public void setTitleSize(int titleSize) {
        mTitleSize = titleSize;
    }

    public int getRightUnit() {
        return mRightUnit;
    }

    public void setRightUnit(int rightUnit) {
        mRightUnit = rightUnit;
    }

    public int getTitleUnit() {
        return mTitleUnit;
    }

    public void setTitleUnit(int titleUnit) {
        mTitleUnit = titleUnit;
    }

    public int getTitleColorRes() {
        return mTitleColorRes;
    }

    public void setTitleColorRes(int titleColorRes) {
        mTitleColorRes = titleColorRes;
    }

    public int getRightImageRes() {
        return mRightImageRes;
    }

    public void setRightImageRes(int rightImageRes) {
        mRightImageRes = rightImageRes;
    }

    public int getRightTextSize() {
        return mRightTextSize;
    }

    public void setRightTextSize(int rightTextSize) {
        mRightTextSize = rightTextSize;
    }

    public int getRightTextColorRes() {
        return mRightTextColorRes;
    }

    public void setRightTextColorRes(int rightTextColorRes) {
        mRightTextColorRes = rightTextColorRes;
    }
}
