package com.llj.architecturedemo.ui.presenter

import com.llj.architecturedemo.repository.HomeRepository
import com.llj.architecturedemo.ui.model.BabyHomeModuleVo
import com.llj.architecturedemo.ui.view.IVLayoutView
import com.llj.component.service.ComponentBaseActivityPresenter
import com.llj.lib.net.observer.BaseApiObserver
import com.llj.lib.net.response.BaseResponse
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/24
 */
class VLayoutPresenter @Inject constructor(repository: HomeRepository, view: IVLayoutView)
    : ComponentBaseActivityPresenter<HomeRepository, IVLayoutView>(repository, view) {


    fun getHomeData(isShowDialog: Boolean) {
        var single = repository.getBabyHome(HashMap())

        if (isShowDialog) {
            single = single.doOnSubscribe(view).doFinally(view)
        }

        //Observer
        val baseApiObserver = object : BaseApiObserver<List<BabyHomeModuleVo?>?>(view?.getLoadingDialog()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                view?.addDisposable(getRequestId(), d)
            }

            override fun onSuccess(response: BaseResponse<List<BabyHomeModuleVo?>?>) {
                super.onSuccess(response)
                view?.onDataSuccess1(response, getRequestId())

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                view?.onDataError(-1, t, getRequestId())
            }
        }

        //subscribe
        subscribeApi(single, baseApiObserver)
    }


    fun getWeddingHome(isShowDialog: Boolean) {
        var single = repository.getWeddingHome(HashMap())

        if (isShowDialog) {
            single = single.doOnSubscribe(view).doFinally(view)
        }

        //Observer
        val baseApiObserver = object : BaseApiObserver<List<BabyHomeModuleVo?>?>(view?.getLoadingDialog()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                view?.addDisposable(getRequestId(), d)
            }

            override fun onSuccess(response: BaseResponse<List<BabyHomeModuleVo?>?>) {
                super.onSuccess(response)
                view?.onDataSuccess1(response, getRequestId())

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                view?.onDataError(-1, t, getRequestId())
            }
        }

        //subscribe
        subscribeApi(single, baseApiObserver)
    }
}
