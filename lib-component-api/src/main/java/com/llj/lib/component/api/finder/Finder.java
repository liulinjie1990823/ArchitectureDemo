package com.llj.lib.component.api.finder;


import android.view.View;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
public interface Finder<T> {
    void inject(T host, View source);
}
