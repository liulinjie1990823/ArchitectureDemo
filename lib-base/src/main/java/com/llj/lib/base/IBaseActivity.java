package com.llj.lib.base;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public interface IBaseActivity extends IActivityStack {

    void initLifecycleObserver(@NonNull Lifecycle lifecycle);

    void superOnBackPressed();

    void backToLauncher(boolean nonRoot);
}
