package com.llj.lib.base

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.llj.lib.base.mvp.IBasePresenter
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/15
 */
abstract class MvpBaseActivity<P : IBasePresenter, V : ViewBinding> : MvcBaseActivity<V>() {

    @Inject
    lateinit var mPresenter: P

    @CallSuper
    override fun onDestroy() {
        mPresenter.destroy()

        super.onDestroy()
    }

    override fun initLifecycleObserver(lifecycle: Lifecycle) {
        //将mPresenter作为生命周期观察者添加到lifecycle中
        lifecycle.addObserver(mPresenter)
    }

}
