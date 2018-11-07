package com.llj.architecturedemo.ui.presenter

import android.Manifest
import android.app.AlertDialog
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import com.llj.architecturedemo.repository.MobileRepository
import com.llj.architecturedemo.ui.view.IRequestView
import com.llj.lib.base.mvp.BaseActivityPresenter
import com.llj.lib.utils.AToastUtils
import com.llj.lib.utils.helper.Utils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
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

        AndPermission.with(Utils.getApp())
                .runtime()
                .permission(Manifest.permission.READ_PHONE_STATE)
                .onGranted {
                    val mobileLivData =mRepository?.getMobile("13188888888", mView)
                    mobileLivData?.removeObservers(mView)
                    mobileLivData?.observe(mView, Observer { baseResponse ->

                        if (baseResponse != null) {
                            mView.toast(baseResponse.data)
                        }

                    })
                }
                .onDenied { permissions ->
                    AToastUtils.show(permissions?.toString())
                }
                .rationale { context, permissions, executor ->
                    val permissionNames = Permission.transformText(context, permissions)
                    val message = "读取电话状态"

                    AlertDialog.Builder(context)
                            .setCancelable(false)
                            .setTitle("提示")
                            .setMessage(message)
                            .setPositiveButton("继续") { dialog, which -> executor.execute() }
                            .setNegativeButton("取消") { dialog, which -> executor.cancel() }
                            .show()
                }
                .start()

    }

}