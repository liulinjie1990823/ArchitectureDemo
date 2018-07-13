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
abstract class BaseApiObserver<Data> : SingleObserver<BaseResponse<Data>>, IObserverTag {
    private lateinit var mDisposable: Disposable
    private var mTag: Any = 0



    constructor(tag: Any) {
        mTag = tag
    }

    constructor(iTag: ITag?) {
        mTag = iTag?.getRequestTag() ?: 0
    }

    constructor()

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
    }

    override fun onSuccess(response: BaseResponse<Data>) {

    }

    override fun onError(t: Throwable) {}


    override fun setRequestTag(tag: Any) {
        this.mTag = tag
    }

    override fun getRequestTag(): Any {
        return mTag
    }

    override fun getDisposable(): Disposable {
        return mDisposable
    }
}
