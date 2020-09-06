package com.llj.widget.ui.presenter

import androidx.lifecycle.LifecycleOwner
import com.llj.architecturedemo.repository.MobileRepository
import com.llj.lib.base.mvp.BaseActivityPresenter
import com.llj.widget.ui.view.CircleViewView
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/13
 */
class CircleViewPresenter @Inject constructor(repository: MobileRepository, view: CircleViewView)
    : BaseActivityPresenter<MobileRepository, CircleViewView>(repository, view) {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
    }
}