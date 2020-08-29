package com.llj.architecturedemo.ui.presenter

import com.llj.architecturedemo.repository.HomeRepository
import com.llj.architecturedemo.ui.model.PersonalCenterCountVo
import com.llj.architecturedemo.ui.model.PersonalCenterVo
import com.llj.architecturedemo.ui.view.IMineView
import com.llj.component.service.ComponentBaseActivityPresenter
import com.llj.lib.net.observer.BaseApiObserver
import com.llj.lib.net.response.BaseResponse
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * dmp_hunbohui
 * describe:
 * @author llj
 * date 2018/1/20
 */

class PersonalCenterPresenter @Inject constructor(repository: HomeRepository, view: IMineView)
    : ComponentBaseActivityPresenter<HomeRepository, IMineView>(repository, view) {

    companion object {
        const val GET_PERSONAL_CENTER_INFO = 1
        const val GET_PERSONAL_CENTER_COUNT = 2
    }

    //获取二维码信息
    fun getQrCode(isShowDialog: Boolean) {

        view?.getLoadingDialog()?.setRequestId(view.hashCode())
        val params = view?.getParams1(view?.getLoadingDialog()?.getRequestId() ?: 0) ?: return
        var single = repository.getQrCode(params)
        if (isShowDialog) single = single.doOnSubscribe(view).doFinally(view)

        //Observer
        val baseApiObserver = object : BaseApiObserver<String?>(view!!.hashCode()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                view?.addDisposable(getRequestId(), d)
            }

            override fun onSuccess(response: BaseResponse<String?>) {
                super.onSuccess(response)
                view?.onDataSuccess3(response, getRequestId())

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                view?.onDataError(GET_PERSONAL_CENTER_INFO, t, getRequestId())
            }
        }

        //subscribe
        subscribeApi(single, baseApiObserver)
    }

    //获取个人中心信息
    fun getPersonalCenterInfo(isShowDialog: Boolean) {
        view?.getLoadingDialog()?.setRequestId(view.hashCode())
        val params = view?.getParams1(view?.getLoadingDialog()?.getRequestId() ?: 0) ?: return
        var single = repository.getPersonalCenterInfo(params)
        if (isShowDialog) single = single.doOnSubscribe(view).doFinally(view)

        //Observer
        val baseApiObserver = object : BaseApiObserver<PersonalCenterVo?>(view!!.hashCode()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                view?.addDisposable(getRequestId(), d)
            }

            override fun onSuccess(response: BaseResponse<PersonalCenterVo?>) {
                super.onSuccess(response)
                view?.onDataSuccess1(response, getRequestId())
            }

            override fun onError(t: Throwable) {
                super.onError(t)
                view?.onDataError(GET_PERSONAL_CENTER_INFO, t, getRequestId())
            }
        }

        //subscribe
        subscribeApi(single, baseApiObserver)
    }

    //获取个人中心数量
    fun getPersonalCenterCount(isShowDialog: Boolean) {

        view?.getLoadingDialog()?.setRequestId(view.hashCode())
        val params = view?.getParams1(view?.getLoadingDialog()?.getRequestId() ?: 0) ?: return
        var single = repository.getPersonalCenterCount(params)
        if (isShowDialog) single = single.doOnSubscribe(view).doFinally(view)

        //Observer
        val baseApiObserver = object : BaseApiObserver<PersonalCenterCountVo?>(view!!.hashCode()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                view?.addDisposable(getRequestId(), d)
            }

            override fun onSuccess(response: BaseResponse<PersonalCenterCountVo?>) {
                super.onSuccess(response)
                view?.onDataSuccess2(response, getRequestId())

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                view?.onDataError(GET_PERSONAL_CENTER_COUNT, t, getRequestId())
            }
        }

        //subscribe
        subscribeApi(single, baseApiObserver)
    }
}
