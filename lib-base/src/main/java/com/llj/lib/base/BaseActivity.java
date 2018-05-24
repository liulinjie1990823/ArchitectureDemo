package com.llj.lib.base;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.stetho.common.LogUtil;
import com.llj.lib.base.mvp.IPresenter;
import com.llj.lib.base.widget.LoadingDialog;
import com.llj.lib.net.IRequestDialog;
import com.llj.lib.utils.AInputMethodManagerUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/15
 */
public abstract class BaseActivity<P extends IPresenter, D extends IRequestDialog> extends AppCompatActivity
        implements IBaseActivity, ICommon, IUiHandler, IRequestDialogHandler {
    public String TAG_LOG;

    @Inject
    protected P mPresenter;
    @Inject
    protected D mRequestDialog;

    private Unbinder mUnbinder;

    //<editor-fold desc="生命周期">
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);

        addCurrentActivity(this);

        TAG_LOG = getClass().getSimpleName();

        getIntentData(getIntent());

        View layoutView = layoutView();
        if (layoutView == null) {
            setContentView(layoutId());
        } else {
            setContentView(layoutView);
        }
        mUnbinder = ButterKnife.bind(this);

        checkRequestDialog();

        initLifecycleObserver(getLifecycle());

        initViews(savedInstanceState);

        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //防止窗口泄漏，关闭dialog同时结束相关请求
        Dialog requestDialog = (Dialog) getRequestDialog();
        if (requestDialog.isShowing()) {
            requestDialog.cancel();
        }

        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        mUnbinder = null;

        if (mPresenter != null) {
            mPresenter = null;
        }

        //移除列表中的activity
        removeCurrentActivity(this);
    }
    //</editor-fold >

    //<editor-fold desc="IBaseActivity">
    @Override
    public void initLifecycleObserver(@NonNull Lifecycle lifecycle) {
        //将mPresenter作为生命周期观察者添加到lifecycle中
        if (mPresenter != null) {
            lifecycle.addObserver(mPresenter);
        }
    }

    @Override
    public void superOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void backToLauncher() {
        moveTaskToBack(true);
    }
    //</editor-fold >

    //<editor-fold desc="IRequestDialogHandler">
    @Override
    public IRequestDialog getRequestDialog() {
        return mRequestDialog;
    }

    @Override
    public IRequestDialog initRequestDialog() {
        return null;
    }

    public void checkRequestDialog() {
        if (mRequestDialog == null) {
            mRequestDialog = (D) new LoadingDialog(this);
        }
        IRequestDialog requestDialog = getRequestDialog();
        ((Dialog) requestDialog).setOnCancelListener(dialog -> {
            LogUtil.i(TAG_LOG, "cancelOkHttpCall:" + getRequestDialog().getRequestTag());
            cancelOkHttpCall(getRequestDialog().getRequestTag());
        });
    }


    @Override
    public void setRequestTag(int tag) {
        if (getRequestDialog() != null) {
            getRequestDialog().setRequestTag(tag);
        }
    }

    @Override
    public int getRequestTag() {
        if (getRequestDialog() != null) {
            return getRequestDialog().getRequestTag();
        }
        return -1;
    }
    //</editor-fold >

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

}
