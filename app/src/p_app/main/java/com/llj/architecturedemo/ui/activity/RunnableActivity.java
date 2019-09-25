package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.R;
import com.llj.component.service.ComponentMvcBaseActivity;
import com.llj.component.service.arouter.CRouter;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.internal.DebouncingOnClickListener;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-09-23
 */
@Route(path = CRouter.APP_RUNNABLE_ACTIVITY)
public class RunnableActivity extends ComponentMvcBaseActivity {
    @BindView(R.id.tv_start) TextView mStart;
    @BindView(R.id.tv_stop)  TextView mStop;

    @Override
    public int layoutId() {
        return R.layout.runnable_activity;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        DebouncingOnClickListener onClickListener = new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                if (v.getId() == R.id.tv_start) {
                    mStart.post(mRunnable);
                } else if (v.getId() == R.id.tv_stop) {
                    mStart.removeCallbacks(mRunnable);
                }
            }
        };
        mStart.setOnClickListener(onClickListener);
        mStop.setOnClickListener(onClickListener);
    }

    private int mCount;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(getMTagLog(), "" + (mCount++));


            mStart.postDelayed(mRunnable, 5000);
        }
    };

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStart.removeCallbacks(mRunnable);
    }
}
