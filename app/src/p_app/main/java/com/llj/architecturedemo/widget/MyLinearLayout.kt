package com.llj.architecturedemo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.llj.lib.utils.LogUtil

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/16
 */
class MyLinearLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mTag: String = "MyLinearLayout"

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> LogUtil.e(mTag, "dispatchTouchEvent:ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> LogUtil.e(mTag, "dispatchTouchEvent:ACTION_MOVE")
            MotionEvent.ACTION_UP -> LogUtil.e(mTag, "dispatchTouchEvent:ACTION_UP")
        }
        val dispatchTouchEvent = super.dispatchTouchEvent(ev)

        LogUtil.e(mTag, "dispatchTouchEvent:$dispatchTouchEvent")

        return dispatchTouchEvent
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> LogUtil.e(mTag, "onInterceptTouchEvent:ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> LogUtil.e(mTag, "onInterceptTouchEvent:ACTION_MOVE")
            MotionEvent.ACTION_UP -> LogUtil.e(mTag, "onInterceptTouchEvent:ACTION_UP")
        }
        var onInterceptTouchEvent = super.onInterceptTouchEvent(ev)

        onInterceptTouchEvent = true

        LogUtil.e(mTag, "onInterceptTouchEvent:$onInterceptTouchEvent")

        return onInterceptTouchEvent
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> LogUtil.e(mTag, "onTouchEvent:ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> LogUtil.e(mTag, "onTouchEvent:ACTION_MOVE")
            MotionEvent.ACTION_UP -> LogUtil.e(mTag, "onTouchEvent:ACTION_UP")
        }
        var onTouchEvent = super.onTouchEvent(ev)

        onTouchEvent = true

        LogUtil.e(mTag, "onTouchEvent:$onTouchEvent")

        return onTouchEvent
    }

}