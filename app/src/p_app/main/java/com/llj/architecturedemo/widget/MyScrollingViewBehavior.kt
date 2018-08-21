package com.llj.architecturedemo.widget

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/17
 */
class MyScrollingViewBehavior : CoordinatorLayout.Behavior<View> {
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        child?.y = dependency?.y ?: 0f
        return true
    }
}