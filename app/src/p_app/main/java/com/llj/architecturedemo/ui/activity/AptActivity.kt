package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.R2
import com.llj.application.router.CJump
import com.llj.application.router.CRouter
import com.llj.lib.component.annotation.BindView
import com.llj.lib.component.api.finder.NeacyFinder
import com.llj.lib.jump.annotation.Jump

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
//@Route(outPath = CRouter.APP_APT_ACTIVITY)
@Jump(outPath = CJump.CIW_APT_ACTIVITY, inPath = CRouter.APP_APT_ACTIVITY, desc = "AptActivity")
class AptActivity : MainMvcBaseActivity<ViewBinding>() {
    @BindView(R2.id.root) lateinit var mRecyclerView: ConstraintLayout

    override fun layoutId(): Int {
        return R.layout.activity_apt
    }

    override fun initViews(savedInstanceState: Bundle?) {
        NeacyFinder.inject(this)
    }

    override fun initData() {
    }


}