package com.llj.architecturedemo.ui.presenter

import com.llj.architecturedemo.repository.HomeRepository
import com.llj.architecturedemo.ui.model.TabListVo
import com.llj.architecturedemo.ui.view.IPreLoadingView
import com.llj.component.service.ADBaseServicePresenter
import com.llj.lib.net.observer.BaseApiObserver
import com.llj.lib.net.response.BaseResponse
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/17
 */
class PreLoadingPresenter @Inject constructor(repository: HomeRepository, view: IPreLoadingView)
    : ADBaseServicePresenter<HomeRepository, IPreLoadingView>(repository, view) {


    //获取二维码信息
    fun getTabBar() {
        val single = mRepository!!.getTabBar(mView.getParams())

        //Observer
        val baseApiObserver = object : BaseApiObserver<TabListVo?>() {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                mView.addDisposable(getRequestTag(), d)
            }

            override fun onSuccess(response: BaseResponse<TabListVo?>) {
                super.onSuccess(response)
                mView.onDataSuccess(response)

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                mView.onDataError(t)
            }
        }

        //subscribe
        subscribeApi(single, baseApiObserver)
    }
}
