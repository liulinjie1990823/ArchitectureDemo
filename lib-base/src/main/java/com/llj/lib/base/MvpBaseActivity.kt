package com.llj.lib.base

import android.arch.lifecycle.Lifecycle
import android.support.annotation.CallSuper
import com.llj.lib.base.mvp.IBasePresenter
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/15
 */
abstract class MvpBaseActivity<P : IBasePresenter> : MvcBaseActivity(){

    @Inject lateinit var mPresenter: P

    @CallSuper
    override fun onDestroy() {
        mPresenter.destroy()

        super.onDestroy()
    }
    override fun initLifecycleObserver(lifecycle: Lifecycle) {
        //将mPresenter作为生命周期观察者添加到lifecycle中
        lifecycle.addObserver(mPresenter)
    }

    override fun getPageKey(): String {
        return "test"
    }

}
