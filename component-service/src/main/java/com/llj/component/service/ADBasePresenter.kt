package com.llj.component.service

import com.llj.lib.base.mvp.BasePresenter
import com.llj.lib.base.mvp.BaseRepository
import com.llj.lib.base.mvp.IView
import com.llj.lib.net.RxApiManager
import com.llj.lib.net.observer.BaseApiObserver
import com.llj.lib.net.response.BaseResponse
import io.reactivex.Single
import retrofit2.Response

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/5
 */
abstract class ADBasePresenter<R : BaseRepository, V : IView> : BasePresenter<R, V> {
    constructor(repository: R, view: V) : super(repository, view)

    constructor(view: V) : super(view)


    fun <Data> subscribeApi(single: Single<Response<BaseResponse<Data>>>, observer: BaseApiObserver<Data>) {
        RxApiManager.get().subscribeApi(single, mView.bindRequestLifecycle(), observer)
    }
}
