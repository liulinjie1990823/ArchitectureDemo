package com.llj.lib.base

import android.app.IntentService
import android.support.v4.util.ArrayMap
import com.llj.lib.base.mvp.IBasePresenter
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/7
 */
  abstract class MvpBaseIntentService<P : IBasePresenter>(name: String) :  ITask,IUiHandler,
        IntentService(name) {

    @Inject lateinit var mPresenter: P

    private val mCancelableTask: ArrayMap<Any, Disposable> = ArrayMap()

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    //<editor-fold desc="任务处理">
    override fun addDisposable(tag: Any, disposable: Disposable) {
        mCancelableTask[tag] = disposable
    }

    override fun removeDisposable(tag: Any?) {
        val disposable = mCancelableTask[tag] ?: return

        if (!disposable.isDisposed) {
            disposable.dispose()
            mCancelableTask.remove(tag)
        }
    }

    override fun removeAllDisposable() {
        if (mCancelableTask.isEmpty) {
            return
        }
        val keys = mCancelableTask.keys
        for (apiKey in keys) {
            removeDisposable(apiKey)
        }
    }
    //</editor-fold >

    override fun onDestroy() {
        super.onDestroy()
        removeAllDisposable()
    }
}
