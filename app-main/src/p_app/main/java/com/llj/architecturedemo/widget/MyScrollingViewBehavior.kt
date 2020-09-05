package com.llj.architecturedemo.widget

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/17
 */
class MyScrollingViewBehavior : androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior<View> {
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun layoutDependsOn(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is androidx.recyclerview.widget.RecyclerView
    }

    override fun onDependentViewChanged(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: View, dependency: View): Boolean {
        child.y = dependency.y
        return true
    }
}