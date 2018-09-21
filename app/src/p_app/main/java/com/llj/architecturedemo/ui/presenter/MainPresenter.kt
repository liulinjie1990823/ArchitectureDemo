package com.llj.architecturedemo.ui.presenter

import android.arch.lifecycle.LifecycleOwner
import com.llj.architecturedemo.repository.MobileRepository
import com.llj.architecturedemo.ui.view.MainContractView
import com.llj.lib.base.mvp.BasePresenter
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/17
 */
class MainPresenter @Inject constructor(repository: MobileRepository, view: MainContractView)
    : BasePresenter<MobileRepository, MainContractView>(repository, view) {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
    }

}
