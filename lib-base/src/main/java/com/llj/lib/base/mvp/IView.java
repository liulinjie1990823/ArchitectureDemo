package com.llj.lib.base.mvp;

import android.arch.lifecycle.LifecycleOwner;

import com.llj.lib.base.ILoadingDialogHandler;
import com.llj.lib.net.response.BaseResponse;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
public interface IView extends LifecycleOwner, ILoadingDialogHandler<Disposable> {

    default <Data> AutoDisposeConverter<BaseResponse<Data>> bindRequestLifecycle() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle()));
    }
//    /**
//     * 显示加载
//     */
//    default void showLoading() {
//
//    }
//
//    /**
//     * 隐藏加载
//     */
//    default void hideLoading() {
//
//    }
//
//    /**
//     * 显示信息
//     *
//     * @param message 消息内容, 不能为 {@code null}
//     */
//    void showMessage(@NonNull String message);
//
//    /**
//     * 跳转 {@link Activity}
//     *
//     * @param intent {@code intent} 不能为 {@code null}
//     */
//    default void launchActivity(@NonNull Intent intent) {
////        checkNotNull(intent);
////        ArmsUtils.startActivity(intent);
//    }
//
//    /**
//     * 杀死自己
//     */
//    default   void killMyself() {
//
//    }
}
