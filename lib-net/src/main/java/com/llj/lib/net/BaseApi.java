package com.llj.lib.net;

import android.content.Context;
import androidx.annotation.NonNull;

/**
 * https://blog.csdn.net/a19881029/article/details/14002273
 * https://blog.csdn.net/u010386612/article/details/52216146
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public abstract class BaseApi<T> extends BaseRetrofitManager<T>{
    public BaseApi() {
    }

    public BaseApi(@NonNull Context context) {
        super(context);
    }
}
