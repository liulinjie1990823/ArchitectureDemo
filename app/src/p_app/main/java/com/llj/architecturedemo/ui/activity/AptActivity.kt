package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CJump
import com.llj.component.service.arouter.CRouter
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
class AptActivity : AppMvcBaseActivity<ViewBinding>() {
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