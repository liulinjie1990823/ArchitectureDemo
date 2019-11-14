package com.llj.architecturedemo.ui.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.llj.architecturedemo.repository.MobileRepository
import com.llj.architecturedemo.ui.view.IRequestView
import com.llj.component.service.permission.PermissionManager
import com.llj.lib.base.mvp.BaseActivityPresenter
import com.llj.lib.utils.helper.Utils
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
class RequestPresenter @Inject constructor(repository: MobileRepository, view: IRequestView)
    : BaseActivityPresenter<MobileRepository, IRequestView>(repository, view) {


    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        PermissionManager.checkPhoneState(Utils.getApp(), PermissionManager.PermissionListener {
            val mobileLivData = repository.getMobile("13188888888", view!!)
            mobileLivData.removeObservers(view!!)
            mobileLivData.observe(view!!, Observer { baseResponse ->

                if (baseResponse != null) {
                    view?.toast(baseResponse.data)
                }

            })
        })
    }

}