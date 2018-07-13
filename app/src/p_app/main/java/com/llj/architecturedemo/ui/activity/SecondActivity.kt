package com.llj.architecturedemo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MyBaseActivity
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.mvp.IPresenter

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/3
 */
@Route(path = CRouter.APP_SECOND_ACTIVITY)
class SecondActivity : MyBaseActivity<IPresenter>() {

    @BindView(R.id.tv_click) lateinit var mTvClick: TextView

    override fun layoutId(): Int {
        return R.layout.activity_second
    }

    override fun initViews(savedInstanceState: Bundle?) {
        mTvClick.setOnClickListener {
            CRouter.start(CRouter.APP_MAIN_ACTIVITY)
        }
    }

    override fun initData() {
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SecondActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
}
