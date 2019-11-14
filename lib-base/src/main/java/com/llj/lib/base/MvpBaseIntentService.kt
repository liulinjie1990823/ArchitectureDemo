package com.llj.lib.base

import android.app.IntentService
import androidx.collection.ArrayMap
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
abstract class MvpBaseIntentService<P : IBasePresenter>(name: String) : ITask, IUiHandler,
        IntentService(name) {

    @Inject lateinit var mPresenter: P

    private val mCancelableTask: androidx.collection.ArrayMap<Int, Disposable> = androidx.collection.ArrayMap()

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    //<editor-fold desc="任务处理">
    override fun addDisposable(taskId: Int, disposable: Disposable) {
        mCancelableTask[taskId] = disposable
    }

    override fun removeDisposable(taskId: Int?) {
        val disposable = mCancelableTask[taskId] ?: return

        if (!disposable.isDisposed) {
            disposable.dispose()
            mCancelableTask.remove(taskId)
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
