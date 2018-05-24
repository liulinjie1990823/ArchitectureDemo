package com.llj.lib.base;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public interface IFragment {

    void initLifecycleObserver(@NonNull Lifecycle lifecycle);
}
