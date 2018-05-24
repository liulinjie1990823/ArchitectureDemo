package com.llj.lib.base.listeners;

import android.os.SystemClock;
import android.view.View;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public abstract class OnMyClickListener implements View.OnClickListener {
    private static final long CLICK_INTERVAL = 400;
    private long mLastClickTime;

    @Override
    public void onClick(View v) {
        if (clickEnable()) {
            onCanClick(v);
        }
    }

    public abstract void onCanClick(View v);

    protected boolean clickEnable() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < CLICK_INTERVAL) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        return true;
    }
}
