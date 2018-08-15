package com.llj.architecturedemo.presenter

import android.arch.lifecycle.LifecycleOwner
import com.llj.architecturedemo.repository.MobileRepository
import com.llj.architecturedemo.view.MainContractView
import com.llj.lib.base.mvp.BasePresenter
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/17
 */
class MainPresenter @Inject constructor(repository: MobileRepository, view: MainContractView)
    : BasePresenter<MobileRepository, MainContractView>(repository, view) {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

//        val mobileLivData = mRepository?.getMobile("13188888888", mView)
//        mobileLivData?.removeObservers(mView)
//        mobileLivData?.observe(mView, Observer { baseResponse ->
//
//            if (baseResponse != null) {
//                mView.toast(baseResponse.data)
//            }
//
//        })
    }
}
