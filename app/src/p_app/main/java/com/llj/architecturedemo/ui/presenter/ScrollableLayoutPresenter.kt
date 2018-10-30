package com.llj.architecturedemo.ui.presenter

import com.llj.architecturedemo.repository.HomeRepository
import com.llj.architecturedemo.ui.model.BabyHomeModuleVo
import com.llj.architecturedemo.ui.view.IScrollableLayoutView
import com.llj.lib.base.mvp.BasePresenter
import com.llj.lib.net.RxApiManager
import com.llj.lib.net.observer.BaseApiObserver
import com.llj.lib.net.response.BaseResponse
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/24
 */
class ScrollableLayoutPresenter @Inject constructor(repository: HomeRepository, view: IScrollableLayoutView)
    : BasePresenter<HomeRepository, IScrollableLayoutView>(repository,view) {


    fun getWeddingHome(isShowDialog: Boolean) {
        var babyHome = mRepository?.getWeddingHome()

        if (isShowDialog) {
            babyHome = babyHome?.doOnSubscribe(mView)?.doFinally(mView)
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
        RxApiManager.get().subscribeApi(
                babyHome,
                mView.bindRequestLifecycle(),
                baseApiObserver)
    }
}
