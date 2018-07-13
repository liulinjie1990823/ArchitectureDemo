package com.llj.widget.ui.presenter

import android.arch.lifecycle.LifecycleOwner
import com.llj.architecturedemo.repository.MobileRepository
import com.llj.lib.base.mvp.BasePresenter
import com.llj.widget.ui.view.CircleViewView
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/13
 */
class CircleViewPresenter @Inject constructor(view: CircleViewView)
    : BasePresenter<MobileRepository, CircleViewView>(view) {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
    }
}