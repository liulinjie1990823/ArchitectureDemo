package com.llj.architecturedemo;

import com.llj.lib.net.BaseApiObserver;
import com.llj.lib.net.IRequestDialog;
import com.llj.lib.net.IResponse;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/8
 */
public class ApiObserver<T> extends BaseApiObserver<IResponse<T>> {
    public ApiObserver(int tag) {
        super(tag);
    }

    public ApiObserver(IRequestDialog IRequestDialog) {
        super(IRequestDialog);
    }
}
