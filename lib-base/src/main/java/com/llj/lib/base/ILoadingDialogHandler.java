package com.llj.lib.base;

import com.llj.lib.net.observer.ITag;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * ArchitectureDemo
 * describe: 供activity和dialog使用，
 * author liulj
 * date 2018/5/24
 */
public interface ILoadingDialogHandler<T extends
        Disposable> extends Consumer<T>,
        Action,
        ITag {
    ITag initLoadingDialog();

    ITag getLoadingDialog();

    default void showLoadingDialog() {
    }

    default void dismissLoadingDialog() {
    }

    //显示
    @Override
    default void accept(Disposable o) throws Exception {
        showLoadingDialog();
    }

    //隐藏
    @Override
    default void run() throws Exception {
        dismissLoadingDialog();
    }
}
