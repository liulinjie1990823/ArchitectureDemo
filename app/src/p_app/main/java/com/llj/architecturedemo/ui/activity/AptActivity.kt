package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.MvcBaseActivity
import com.llj.lib.component.api.finder.NeacyFinder

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
@Route(path = CRouter.APP_APT_ACTIVITY)
class AptActivity : MvcBaseActivity() {
    @BindView(R.id.root) lateinit var mRecyclerView: ConstraintLayout

    override fun layoutId(): Int {
      return  R.layout.activity_apt
    }

    override fun initViews(savedInstanceState: Bundle?) {
        NeacyFinder.inject(this)
    }

    override fun initData() {
    }


}