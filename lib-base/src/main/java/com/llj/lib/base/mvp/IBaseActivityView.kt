package com.llj.lib.base.mvp

import androidx.lifecycle.LifecycleOwner
import com.llj.lib.base.ILoadingDialogHandler
import com.llj.lib.net.response.BaseResponse
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 * ArchitectureDemo
 * describe:需要ITask，ILoadingDialogHandler，LifecycleOwner。
 * @author llj
 * @date 2018/5/16
 */
interface IBaseActivityView : IBaseView, ILoadingDialogHandler , LifecycleOwner {

    /**
     *用AutoDispose绑定生命周期
     */
    fun <Data> bindRequestLifecycle(): AutoDisposeConverter<BaseResponse<Data>> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle))
    }

}
