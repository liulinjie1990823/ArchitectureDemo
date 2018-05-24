package com.llj.lib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public interface ICommon {
    default void getIntentData(Intent intent) {
    }

    default View layoutView() {
        return null;
    }

    @LayoutRes
    int layoutId();

    void initViews(Bundle savedInstanceState);

    void initData();
}
