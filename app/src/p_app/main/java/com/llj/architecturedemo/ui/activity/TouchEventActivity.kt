package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.view.MotionEvent
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.MvcBaseActivity
import com.llj.lib.utils.LogUtil

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/16
 */
@Route(path = CRouter.APP_TOUCH_EVENT_ACTIVITY)
class TouchEventActivity : MvcBaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_touch_event
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> LogUtil.e(mTagLog, "dispatchTouchEvent:ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> LogUtil.e(mTagLog, "dispatchTouchEvent:ACTION_MOVE")
            MotionEvent.ACTION_UP -> LogUtil.e(mTagLog, "dispatchTouchEvent:ACTION_UP")
        }
        val dispatchTouchEvent = super.dispatchTouchEvent(ev)

        LogUtil.e(mTagLog, "dispatchTouchEvent:$dispatchTouchEvent")

        return dispatchTouchEvent
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> LogUtil.e(mTagLog, "onTouchEvent:ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> LogUtil.e(mTagLog, "onTouchEvent:ACTION_MOVE")
            MotionEvent.ACTION_UP -> LogUtil.e(mTagLog, "onTouchEvent:ACTION_UP")
        }
        val onTouchEvent = super.onTouchEvent(event)

        LogUtil.e(mTagLog, "onTouchEvent:$onTouchEvent")

        return onTouchEvent
    }
}