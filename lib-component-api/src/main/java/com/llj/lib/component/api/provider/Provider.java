package com.llj.lib.component.api.provider;

import android.view.View;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
public interface Provider {
    View findView(Object viewRoot, int view_id);
}