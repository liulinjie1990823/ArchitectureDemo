package com.llj.lib.base.mvp;

import android.arch.lifecycle.LifecycleOwner;

import com.llj.lib.base.ILoadingDialogHandler;
import com.llj.lib.base.ITask;
import com.llj.lib.net.response.BaseResponse;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
public interface IView extends LifecycleOwner, ILoadingDialogHandler, ITask {

    default <Data> AutoDisposeConverter<BaseResponse<Data>> bindRequestLifecycle() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle()));
    }
}
