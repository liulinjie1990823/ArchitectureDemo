package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.R
import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.architecturedemo.ui.presenter.RequestPresenter
import com.llj.architecturedemo.ui.view.IRequestView
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.MvpBaseActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
@Route(path = CRouter.APP_REQUEST_ACTIVITY)
class RequestActivity : MvpBaseActivity<RequestPresenter>(), IRequestView {
    override fun initData() {
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun layoutId(): Int {
        return R.layout.activity_request
    }

    override fun toast(mobile: MobileEntity?) {

    }
}