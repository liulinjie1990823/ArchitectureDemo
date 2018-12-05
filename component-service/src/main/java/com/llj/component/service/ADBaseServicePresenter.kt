package com.llj.component.service

import com.llj.lib.base.mvp.BaseRepository
import com.llj.lib.base.mvp.BaseServicePresenter
import com.llj.lib.base.mvp.IBaseView
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
abstract class ADBaseServicePresenter<R : BaseRepository, V : IBaseView> : BaseServicePresenter<R, V> {
    constructor(repository: R, view: V) : super(repository, view)

    constructor(view: V) : super(view)


    fun <Data> subscribeApi(single: Single<Response<BaseResponse<Data>>>, observer: BaseApiObserver<Data>) {
        RxApiManager.get().subscribeApi(single, null, observer)
    }
}
