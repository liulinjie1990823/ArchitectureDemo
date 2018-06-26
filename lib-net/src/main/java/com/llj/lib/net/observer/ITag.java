package com.llj.lib.net.observer;

import com.llj.lib.net.RxApiManager;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/11
 */
public interface ITag {
    void setRequestTag(int tag);

    int getRequestTag();

    default void cancelOkHttpCall(int requestTag) {
        RxApiManager.get().cancel(requestTag);
    }
}
