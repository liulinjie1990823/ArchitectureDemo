package com.llj.lib.net.observer

import com.llj.lib.net.response.BaseResponse

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
abstract class BaseApiObserver<Data> : SingleObserver<BaseResponse<Data>>, IObserverTask {
    private lateinit var mDisposable: Disposable
    private var mTaskId: Int = 0


    constructor(taskId: Int) {
        mTaskId = taskId
    }

    constructor(iTaskId: ITaskId?) {
        mTaskId = iTaskId?.getRequestId() ?: 0
    }

    constructor()

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
    }

    override fun onSuccess(response: BaseResponse<Data>) {

    }

    override fun onError(t: Throwable) {}


    override fun setRequestId(taskId: Int) {
        this.mTaskId = taskId
    }

    override fun getRequestId(): Int {
        return mTaskId
    }

    override fun getDisposable(): Disposable {
        return mDisposable
    }
}
