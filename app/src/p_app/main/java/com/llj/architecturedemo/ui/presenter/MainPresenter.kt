package com.llj.architecturedemo.ui.presenter

import com.llj.architecturedemo.repository.HomeRepository
import com.llj.architecturedemo.ui.model.TabListVo
import com.llj.architecturedemo.ui.view.MainContractView
import com.llj.component.service.ComponentBaseActivityPresenter
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
class MainPresenter @Inject constructor(repository: HomeRepository, view: MainContractView)
    : ComponentBaseActivityPresenter<HomeRepository, MainContractView>(repository, view) {


    //获取二维码信息
    fun getTabBar(isShowDialog: Boolean) {
        var single = mRepository!!.getTabBar(mView.getParams1())

        if (isShowDialog) {
            single = single.doOnSubscribe(mView).doFinally(mView)
        }

        //Observer
        val baseApiObserver = object : BaseApiObserver<TabListVo?>(mView.getRequestTag()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                mView.addDisposable(getRequestTag(), d)
            }

            override fun onSuccess(response: BaseResponse<TabListVo?>) {
                super.onSuccess(response)
                mView.onDataSuccess1(response)

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                mView.onDataError(-1,t)
            }
        }

        //subscribe
        subscribeApi(single, baseApiObserver)
    }
}
