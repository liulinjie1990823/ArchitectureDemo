package com.llj.lib.base.mvp

import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/16
 */
open class BaseActivityPresenter<R : BaseRepository, V : IBaseActivityView> : IBasePresenter {
    val mTagLog = this.javaClass.simpleName

    val repository: R
    var view: V? = null

    constructor(repository: R, view: V) {
        this.repository = repository
        this.view = view
    }

    constructor(repository: R) {
        this.repository = repository
    }


    override fun destroy() {
    }

    override fun onCreate(owner: LifecycleOwner) {
        Timber.tag(mTagLog).i("onCreate() state:%s", owner.lifecycle.currentState.name)
    }

    override fun onStart(owner: LifecycleOwner) {
        Timber.tag(mTagLog).i("onStart() state:%s", owner.lifecycle.currentState.name)
    }

    override fun onResume(owner: LifecycleOwner) {
        Timber.tag(mTagLog).i("onResume() state:%s", owner.lifecycle.currentState.name)
    }

    override fun onPause(owner: LifecycleOwner) {
        Timber.tag(mTagLog).i("onPause() state:%s", owner.lifecycle.currentState.name)
    }

    override fun onStop(owner: LifecycleOwner) {
        Timber.i("onStop() state:%s", owner.lifecycle.currentState.name)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Timber.tag(mTagLog).i("onDestroy() state:%s", owner.lifecycle.currentState.name)
        destroy()
        owner.lifecycle.removeObserver(this)
    }
}
