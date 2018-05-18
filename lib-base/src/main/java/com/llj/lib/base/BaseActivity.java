package com.llj.lib.base;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.llj.lib.base.mvp.IPresenter;
import com.llj.lib.utils.AInputMethodManagerUtils;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/15
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity {
    public static String TAG_LOG;

    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);

        TAG_LOG = getClass().getSimpleName();

        getIntentData(getIntent());

        View layoutView = layoutView();
        if (layoutView == null) {
            setContentView(layoutId());
        } else {
            setContentView(layoutView);
        }
        ButterKnife.bind(this);

        initLifecycleObserver(getLifecycle());

        initViews(savedInstanceState);
        initData();
    }

    protected abstract void getIntentData(Intent intent);

    @MainThread
    protected void initLifecycleObserver(@NonNull Lifecycle lifecycle) {
        //将mPresenter作为生命周期观察者添加到lifecycle中
        if (mPresenter != null) {
            lifecycle.addObserver(mPresenter);
        }
    }

    protected abstract View layoutView();

    protected abstract int layoutId();

    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    ///////////////////////////////////////////////////////////////////////////
    // 处理点击外部影藏输入法
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AInputMethodManagerUtils.hideSoftInputFromWindow(this);
        }
        return super.onTouchEvent(event);

    }


    public void superOnBackPressed() {
        super.onBackPressed();
    }
}
