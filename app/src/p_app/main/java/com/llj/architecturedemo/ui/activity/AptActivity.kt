package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import com.llj.architecturedemo.R
import com.llj.lib.base.BaseEvent
import com.llj.lib.base.MvcBaseActivity
import com.llj.lib.component.annotation.BindView
import com.llj.lib.component.api.finder.NeacyFinder
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
//@Route(path = CRouter.APP_APT_ACTIVITY)
class AptActivity : MvcBaseActivity() {
    @BindView(R.id.root) lateinit var mRecyclerView: ConstraintLayout

    override fun layoutId(): Int {
        return R.layout.activity_apt
    }

    override fun initViews(savedInstanceState: Bundle?) {
        NeacyFinder.inject(this)
    }

    override fun initData() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(baseEvent: BaseEvent<String>) {
    }

}