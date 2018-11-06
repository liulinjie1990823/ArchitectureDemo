package com.llj.architecturedemo.ui.presenter

import com.llj.architecturedemo.repository.HomeRepository
import com.llj.architecturedemo.ui.model.BabyHomeModuleVo
import com.llj.architecturedemo.ui.view.IScrollableLayoutView
import com.llj.component.service.ADBasePresenter
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
class ScrollableLayoutPresenter @Inject constructor(repository: HomeRepository, view: IScrollableLayoutView)
    : ADBasePresenter<HomeRepository, IScrollableLayoutView>(repository, view) {


    fun getWeddingHome(isShowDialog: Boolean) {
        var single = mRepository!!.getWeddingHome(HashMap())

        if (isShowDialog) {
            single = single.doOnSubscribe(mView).doFinally(mView)
        }

        //Observer
        val baseApiObserver = object : BaseApiObserver<List<BabyHomeModuleVo?>?>(mView.getRequestTag()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                mView.addDisposable(getRequestTag(), d)
            }

            override fun onSuccess(response: BaseResponse<List<BabyHomeModuleVo?>?>) {
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
