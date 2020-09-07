package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.llj.application.router.CJump
import com.llj.application.router.CRouter
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.lib.jump.annotation.Jump

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2018/9/6
 */
//@Route(outPath = CRouter.APP_APT_ACTIVITY)
@Jump(outPath = CJump.CIW_APT_ACTIVITY, inPath = CRouter.APP_APT_ACTIVITY, desc = "AptActivity")
class AptActivity : MainMvcBaseActivity<ViewBinding>() {

    override fun layoutId(): Int {
        return R.layout.activity_apt
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }


}