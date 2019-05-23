/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2015 cpoopc
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package com.llj.lib.scrollable;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by cpoopc(303727604@qq.com) on 2015-02-10.
 */
public class ScrollableLayout extends LinearLayout {

    private final String tag = "cp:scrollableLayout";
    private       float  mDownX;
    private       float  mDownY;
    private       float  mLastY;

    private int mMinY = 0;
    private int mMaxY = 0;
    private int mHeadHeight;
    private int mExpandHeight;

    //配置的基本参数
    private int       mTouchSlop;
    private int       mMinimumVelocity;
    private int       mMaximumVelocity;
    // 方向
    private DIRECTION mDirection;
    private int       mCurY;
    private int       mLastScrollerY;
    private boolean   mNeedCheckUpDown;//是否需要检查上下滚动
    private boolean   mUpDown;//上下滚动
    private boolean   mDisallowIntercept;
    private boolean   mIsClickHead;//是否点击header区域
    private boolean   mIsClickHeadExpand;//是否点击header扩展区域

    private View      mHeadView;
    private ViewPager childViewPager;

    private Scroller        mScroller;
    private VelocityTracker mVelocityTracker;

    /**
     * 滑动方向 *
     */
    enum DIRECTION {
        UP,// 向上划
        DOWN,// 向下划
        AUTO_COLLAPSE,//自动收缩
        AUTO_EXPAND//自动展开
    }

    //向上划
    public boolean isScrollUp() {
        return mDirection == DIRECTION.UP;
    }

    //向下划
    public boolean isScrollDown() {
        return mDirection == DIRECTION.DOWN;
    }

    public interface OnScrollListener {

        void onScroll(int currentY, int maxY);

    }

    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    private ScrollableHelper mHelper;

    public ScrollableHelper getHelper() {
        return mHelper;
    }

    public ScrollableLayout(Context context) {
        super(context);
        init(context);
    }

    public ScrollableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mHelper = new ScrollableHelper();
        mScroller = new Scroller(context);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    //是否向上滑动到最大值
    public boolean isStickied() {
        return mCurY == mMaxY;
    }

    /**
     * 扩大头部点击滑动范围
     *
     * @param expandHeight
     */
    public void setClickHeadExpand(int expandHeight) {
        mExpandHeight = expandHeight;
    }

    //移到最顶端
    public void expand() {
        mDirection = DIRECTION.AUTO_EXPAND;
        mScroller.startScroll(0, mCurY, 0, -mCurY);
        invalidate();
    }

    //悬停状态
    public void collapse() {
        mDirection = DIRECTION.AUTO_COLLAPSE;
        mScroller.startScroll(0, mCurY, 0, mMaxY - mCurY);
        invalidate();
    }

    public int getMaxY() {
        return mMaxY;
    }

    public boolean isHeadTop() {
        return mCurY == mMinY;
    }

    public boolean canPtr() {
        return mUpDown && mCurY == mMinY && mHelper.isScrollableViewTop();
    }

    public void requestScrollableLayoutDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        mDisallowIntercept = disallowIntercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float currentX = ev.getX();
        float currentY = ev.getY();
        float deltaY;
        int shiftX = (int) Math.abs(currentX - mDownX);
        int shiftY = (int) Math.abs(currentY - mDownY);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDisallowIntercept = false;
                mNeedCheckUpDown = true;
                mUpDown = true;
                mDownX = currentX;
                mDownY = currentY;
                mLastY = currentY;
                checkIsClickHead((int) currentY, mHeadHeight, getScrollY());
                checkIsClickHeadExpand((int) currentY, mHeadHeight, getScrollY());
                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(ev);
                mScroller.forceFinished(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDisallowIntercept) {
                    break;
                }
                initVelocityTrackerIfNotExists();
                mVelocityTracker.addMovement(ev);
                deltaY = mLastY - currentY;
                if (mNeedCheckUpDown) {
                    if (shiftX > mTouchSlop && shiftX > shiftY) {
                        mNeedCheckUpDown = false;
                        mUpDown = false;
                    } else if (shiftY > mTouchSlop && shiftY > shiftX) {
                        mNeedCheckUpDown = false;
                        mUpDown = true;
                    }
                }

                if (mUpDown && shiftY > mTouchSlop && shiftY > shiftX &&
                        (!isStickied() || mHelper.isScrollableViewTop() || mIsClickHeadExpand)) {
                    //上下滑动，
                    if (childViewPager != null) {
                        childViewPager.requestDisallowInterceptTouchEvent(true);
                    }
                    scrollBy(0, (int) (deltaY + 0.5));
                }
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                if (mUpDown && shiftY > shiftX && shiftY > mTouchSlop) {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    float yVelocity = -mVelocityTracker.getYVelocity();
                    boolean allowChild = false;
                    if (Math.abs(yVelocity) > mMinimumVelocity) {
                        mDirection = yVelocity > 0 ? DIRECTION.UP : DIRECTION.DOWN;
                        if ((isScrollUp() && isStickied()) || (isScrollDown() && (!isStickied()) && getScrollY() == 0)) {
                            //向上滑动且header已经置顶
                            allowChild = true;
                        } else {
                            //控制ScrollableLayout本身fling
                            //RecycleView中的fling
                            // public void fling(int velocityX, int velocityY) {
                            //            RecyclerView.this.setScrollState(2);
                            //            this.mLastFlingX = this.mLastFlingY = 0;
                            //            this.mScroller.fling(0, 0, velocityX, velocityY, -2147483648, 2147483647, -2147483648, 2147483647);
                            //            this.postOnAnimation();
                            //        }
                            allowChild = false;
                            mScroller.fling(0, getScrollY(), 0, (int) yVelocity, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
//                            mScroller.computeScrollOffset();
                            mLastScrollerY = getScrollY();
                            invalidate();
                        }
                    }
                    if (!allowChild && (mIsClickHead || !isStickied())) {
                        //不允许子view滚动且点击在header且没有置顶，不需要子view滚动
//                        int action = ev.getAction();
//                        ev.setAction(MotionEvent.ACTION_CANCEL);
//                        boolean dispatchResult = super.dispatchTouchEvent(ev);
//                        ev.setAction(action);
//                        return dispatchResult;
                        return true;
                    } else {
                        //交给子view滚动
                        super.dispatchTouchEvent(ev);
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        super.dispatchTouchEvent(ev);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private int getScrollerVelocity(int distance, int duration) {
        if (mScroller == null) {
            return 0;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return (int) mScroller.getCurrVelocity();
        } else {
            return distance / duration;
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            final int currY = mScroller.getCurrY();
            if (isScrollUp()) {
                // 手势向上划
                if (isStickied()) {
                    //已经置顶的情况，滑动交给子view来滚动
                    int distance = mScroller.getFinalY() - currY;
                    int duration = calcDuration(mScroller.getDuration(), mScroller.timePassed());
                    mHelper.smoothScrollBy(getScrollerVelocity(distance, duration), distance, duration);
                    mScroller.forceFinished(true);
                } else {
                    scrollTo(0, currY);
                    //invalidate();
                }
            } else if (isScrollDown()) {
                // 手势向下划
                if (mHelper.isScrollableViewTop() || mIsClickHeadExpand) {
                    //开始如果子view没有滚完，先通过super.dispatchTouchEvent(ev)让子view滚动
                    //然后接着在这里控制ScrollableLayout里的布局整体移动
                    int deltaY = (currY - mLastScrollerY);
                    int toY = getScrollY() + deltaY;
                    scrollTo(0, toY);
                    if (mCurY <= mMinY) {
                        mScroller.forceFinished(true);
                    }

                }
                invalidate();
            } else {
                //
                // 这里调用View的scrollTo()完成实际的滚动
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                // 必须调用该方法，否则不一定能看到滚动效果
                //invalidate();
            }
            mLastScrollerY = currY;
        }
    }

    //兼容外层布局是SmartRefreshLayout，解决上下滑动冲突
    @Override
    public boolean canScrollVertically(int direction) {
        if (direction > 0) {
            //向上滑动
            return true;
        } else {
            //向下滑动
            return !canPtr();
        }
    }

    @Override
    public void scrollBy(int x, int y) {
        int scrollY = getScrollY();
        int toY = scrollY + y;
        if (toY >= mMaxY) {
            toY = mMaxY;
        } else if (toY <= mMinY) {
            toY = mMinY;
        }
        y = toY - scrollY;
        super.scrollBy(x, y);
    }


    @Override
    public void scrollTo(int x, int y) {
        if (y >= mMaxY) {
            y = mMaxY;
        } else if (y <= mMinY) {
            y = mMinY;
        }
        mCurY = y;
        if (onScrollListener != null) {
            onScrollListener.onScroll(y, mMaxY);
        }
        super.scrollTo(x, y);
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void checkIsClickHead(int downY, int headHeight, int scrollY) {
        mIsClickHead = downY + scrollY <= headHeight;
    }

    private void checkIsClickHeadExpand(int downY, int headHeight, int scrollY) {
        if (mExpandHeight <= 0) {
            mIsClickHeadExpand = false;
        }
        mIsClickHeadExpand = downY + scrollY <= headHeight + mExpandHeight;
    }

    private int calcDuration(int duration, int timePass) {
        return duration - timePass;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeadView = getChildAt(0);
        measureChildWithMargins(mHeadView, widthMeasureSpec, 0, MeasureSpec.UNSPECIFIED, 0);
        mMaxY = mHeadView.getMeasuredHeight();
        mHeadHeight = mHeadView.getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) + mMaxY, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onFinishInflate() {
        if (mHeadView != null && !mHeadView.isClickable()) {
            mHeadView.setClickable(true);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null && childAt instanceof ViewPager) {
                childViewPager = (ViewPager) childAt;
            }
        }
        super.onFinishInflate();
    }
}
