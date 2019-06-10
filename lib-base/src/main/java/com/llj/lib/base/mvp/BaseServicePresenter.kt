package com.llj.lib.base.mvp

import android.arch.lifecycle.LifecycleOwner
import com.llj.lib.utils.LogUtil

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
open class BaseServicePresenter<R : BaseRepository, V : IBaseView> : IBasePresenter {
    val mTagLog = this.javaClass.simpleName

    var repository: R? = null
    var view: V? = null

    constructor(repository: R, view: V) {
        this.repository = repository
        this.view = view
    }

    constructor(repository: R) {
        this.repository = repository
    }

    constructor(view: V) {
        this.view = view
    }

    override fun destroy() {
    }


    override fun onCreate(owner: LifecycleOwner) {
        LogUtil.e(mTagLog, mTagLog + " onCreate()" + "state:" + owner.lifecycle.currentState)
    }

    override fun onStart(owner: LifecycleOwner) {
        LogUtil.e(mTagLog, mTagLog + " onStart()" + "state:" + owner.lifecycle.currentState)
    }

    override fun onResume(owner: LifecycleOwner) {
        LogUtil.e(mTagLog, mTagLog + " onResume()" + "state:" + owner.lifecycle.currentState)
    }

    override fun onPause(owner: LifecycleOwner) {
        LogUtil.e(mTagLog, mTagLog + " onPause()" + "state:" + owner.lifecycle.currentState)
    }

    override fun onStop(owner: LifecycleOwner) {
        LogUtil.e(mTagLog, mTagLog + " onStop()" + "state:" + owner.lifecycle.currentState)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        LogUtil.e(mTagLog, mTagLog + " onDestroy()" + "state:" + owner.lifecycle.currentState)
        destroy()
        owner.lifecycle.removeObserver(this)
    }
}
