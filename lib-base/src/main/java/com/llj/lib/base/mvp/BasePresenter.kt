package com.llj.lib.base.mvp

import android.arch.lifecycle.LifecycleOwner
import com.llj.lib.utils.LogUtil

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
open class BasePresenter<R : BaseRepository, V : IView> : IPresenter {
    protected val mTag = this.javaClass.simpleName

    protected var mRepository: R? = null
    protected lateinit var mView: V

    constructor(repository: R, view: V) {
        this.mRepository = repository
        this.mView = view
        init()
    }

    constructor(view: V) {
        this.mView = view
        init()
    }

    override fun init() {

    }

    override fun destroy() {
    }


    override fun onCreate(owner: LifecycleOwner) {
        LogUtil.e(mTag, mTag + " onCreate()" + "state:" + owner.lifecycle.currentState)
    }

    override fun onStart(owner: LifecycleOwner) {
        LogUtil.e(mTag, mTag + " onStart()" + "state:" + owner.lifecycle.currentState)
    }

    override fun onResume(owner: LifecycleOwner) {
        LogUtil.e(mTag, mTag + " onResume()" + "state:" + owner.lifecycle.currentState)
    }

    override fun onPause(owner: LifecycleOwner) {
        LogUtil.e(mTag, mTag + " onPause()" + "state:" + owner.lifecycle.currentState)
    }

    override fun onStop(owner: LifecycleOwner) {
        LogUtil.e(mTag, mTag + " onStop()" + "state:" + owner.lifecycle.currentState)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        LogUtil.e(mTag, mTag + " onDestroy()" + "state:" + owner.lifecycle.currentState)
        destroy()
        owner.lifecycle.removeObserver(this)
    }
}
