package com.llj.lib.net;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public interface IRequestDialog extends ITag {
    IRequestDialog getRequestDialog();

    default void showRequestDialog() {
        if (getRequestDialog() != null) {
            getRequestDialog().showRequestDialog();
        }
    }

    default void dismissRequestDialog() {
        if (getRequestDialog() != null) {
            getRequestDialog().dismissRequestDialog();
        }
    }

    default void cancelOkHttpCall(int requestTag) {
        RxApiManager.get().cancel(requestTag);
    }
}
