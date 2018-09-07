package com.llj.lib.component.api.provider;

import android.app.Activity;
import android.view.View;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
public class ActivityProvider implements Provider {
    @Override
    public View findView(Object viewRoot, int view_id) {
        return ((Activity) viewRoot).findViewById(view_id);
    }
}