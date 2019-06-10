package com.llj.component.service

import com.llj.lib.base.mvp.BaseActivityPresenter
import com.llj.lib.base.mvp.BaseRepository
import com.llj.lib.base.mvp.IBaseActivityView
import com.llj.lib.net.RxApiManager
import com.llj.lib.net.observer.BaseApiObserver
import com.llj.lib.net.response.BaseResponse
import io.reactivex.Single
import retrofit2.Response

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * date 2018/11/5
 */
abstract class ComponentBaseActivityPresenter<R : BaseRepository, V : IBaseActivityView> : BaseActivityPresenter<R, V> {
    constructor(repository: R, view: V) : super(repository, view)
    constructor(repository: R) : super(repository)


    fun <Data> subscribeApi(single: Single<Response<BaseResponse<Data>>>, observer: BaseApiObserver<Data>) {
        RxApiManager.get().subscribeApi(single, view?.bindRequestLifecycle(), observer)
    }
}
