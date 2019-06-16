package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import com.example.lib.base.annotation.Jump
import com.llj.architecturedemo.R
import com.llj.component.service.ComponentMvcBaseActivity
import com.llj.component.service.arouter.CInner
import com.llj.component.service.arouter.CRouter
import com.llj.lib.component.annotation.BindView
import com.llj.lib.component.api.finder.NeacyFinder

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
//@Route(ciw = CRouter.APP_APT_ACTIVITY)
@Jump(ciw = CInner.CIW_APT_ACTIVITY, route = CRouter.APP_APT_ACTIVITY, desc = "AptActivity")
class AptActivity : ComponentMvcBaseActivity() {
    @BindView(R.id.root) lateinit var mRecyclerView: ConstraintLayout

    override fun layoutId(): Int {
        return R.layout.activity_apt
    }

    override fun initViews(savedInstanceState: Bundle?) {
        NeacyFinder.inject(this)
    }

    override fun initData() {
    }


}