package com.llj.architecturedemo;

import android.support.annotation.NonNull;

import com.llj.lib.net.observer.BaseApiObserver;
import com.llj.lib.net.observer.ITag;
import com.llj.lib.net.response.BaseResponse;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/8
 */
public class ApiObserver<T> extends BaseApiObserver<T> {
    public ApiObserver(ITag iTag) {
        super(iTag);
    }

    @Override
    public void onSuccess(@NonNull BaseResponse<T> response) {
        super.onSuccess(response);
    }

    @Override
    public void onError(@NonNull Throwable t) {
        super.onError(t);
    }
}
