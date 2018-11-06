package com.llj.architecturedemo.ui.presenter

import com.llj.architecturedemo.repository.HomeRepository
import com.llj.architecturedemo.ui.model.PersonalCenterCountVo
import com.llj.architecturedemo.ui.model.PersonalCenterVo
import com.llj.architecturedemo.ui.view.IMineView
import com.llj.component.service.ADBasePresenter
import com.llj.lib.net.observer.BaseApiObserver
import com.llj.lib.net.response.BaseResponse
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * dmp_hunbohui
 * describe:
 * author liulj
 * date 2018/1/20
 */

class PersonalCenterPresenter @Inject constructor(repository: HomeRepository, view: IMineView)
    : ADBasePresenter<HomeRepository, IMineView>(repository, view) {

    companion object {
        const val GET_PERSONAL_CENTER_INFO = 1
        const val GET_PERSONAL_CENTER_COUNT = 2
    }

    //获取二维码信息
    fun getQrCode(isShowDialog: Boolean) {
        var single = mRepository!!.getQrCode(mView.getParams3())

        if (isShowDialog) {
            single = single.doOnSubscribe(mView).doFinally(mView)
        }

        //Observer
        val baseApiObserver = object : BaseApiObserver<String?>(mView.getRequestTag()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                mView.addDisposable(getRequestTag(), d)
            }

            override fun onSuccess(response: BaseResponse<String?>) {
                super.onSuccess(response)
                mView.onDataSuccess3(response)

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                mView.onDataError(GET_PERSONAL_CENTER_INFO, t)
            }
        }

        //subscribe
        subscribeApi(single, baseApiObserver)
    }

    //获取个人中心信息
    fun getPersonalCenterInfo(isShowDialog: Boolean) {
        var single = mRepository!!.getPersonalCenterInfo(mView.getParams1())

        if (isShowDialog) {
            single = single.doOnSubscribe(mView).doFinally(mView)
        }

        //Observer
        val baseApiObserver = object : BaseApiObserver<PersonalCenterVo?>(mView.getRequestTag()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                mView.addDisposable(getRequestTag(), d)
            }

            override fun onSuccess(response: BaseResponse<PersonalCenterVo?>) {
                super.onSuccess(response)
                mView.onDataSuccess1(response)

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                mView.onDataError(GET_PERSONAL_CENTER_INFO, t)
            }
        }

        //subscribe
        subscribeApi(single, baseApiObserver)
    }

    //获取个人中心数量
    fun getPersonalCenterCount(isShowDialog: Boolean) {
        var single = mRepository!!.getPersonalCenterCount(mView.getParams2())

        if (isShowDialog) {
            single = single.doOnSubscribe(mView).doFinally(mView)
        }

        //Observer
        val baseApiObserver = object : BaseApiObserver<PersonalCenterCountVo?>(mView.getRequestTag()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                mView.addDisposable(getRequestTag(), d)
            }

            override fun onSuccess(response: BaseResponse<PersonalCenterCountVo?>) {
                super.onSuccess(response)
                mView.onDataSuccess2(response)

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                mView.onDataError(GET_PERSONAL_CENTER_COUNT, t)
            }
        }

        //subscribe
        subscribeApi(single, baseApiObserver)
    }
}
