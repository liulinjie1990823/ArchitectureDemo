package com.llj.architecturedemo.ui.presenter

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import com.llj.architecturedemo.repository.MobileRepository
import com.llj.architecturedemo.ui.view.IRequestView
import com.llj.lib.base.mvp.BasePresenter
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
class RequestPresenter @Inject constructor(repository: MobileRepository, view: IRequestView)
    : BasePresenter<MobileRepository, IRequestView>(repository, view) {


    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        val mobileLivData = mRepository?.getMobile("13188888888", mView)
        mobileLivData?.removeObservers(mView)
        mobileLivData?.observe(mView, Observer { baseResponse ->

            if (baseResponse != null) {
                mView.toast(baseResponse.data)
            }

        })
    }

}