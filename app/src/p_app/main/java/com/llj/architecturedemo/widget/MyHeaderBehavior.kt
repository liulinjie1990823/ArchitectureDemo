package com.llj.architecturedemo.widget

import android.content.Context
import com.google.android.material.appbar.AppBarLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/17
 */
class MyHeaderBehavior : AppBarLayout.Behavior {
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onNestedPreScroll(coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout, child: AppBarLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        //        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        //AppBarLayout实际已经在super中发生滚动，本来应该通过offset对ViewPager进行移动，但是现在ViewPager是match的，没有位于AppBarLayout下面，所以他里面的RecycleView也应该发生
        // 滑动。所以这里强制设置消耗为0，RecycleView中会判断这里如果消耗为0则RecycleView可以继续滑动

        setTopAndBottomOffset(Math.min(topAndBottomOffset - dy, 0))
        consumed[1] = 0
    }

    override fun onNestedScroll(coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout, child: AppBarLayout, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        //将消耗的dyConsumed传给super的dyUnconsumed,因为
        //        setTopAndBottomOffset(topAndBottomOffset - dyConsumed)
    }
}