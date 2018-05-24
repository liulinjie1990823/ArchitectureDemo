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

        checkRequestDialog();

        initLifecycleObserver(getLifecycle());

        initViews(savedInstanceState);

        initData();
    }

    ///////////////////////////////////////////////////////////////////////////
    // IBaseActivity
    ///////////////////////////////////////////////////////////////////////////
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

    ///////////////////////////////////////////////////////////////////////////
    // 生命周期
    ///////////////////////////////////////////////////////////////////////////
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


    ///////////////////////////////////////////////////////////////////////////
    //IRequestDialogHandler
    ///////////////////////////////////////////////////////////////////////////
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
            LogUtil.i(TAG_LOG, "cancelOkHttpCall:" + getRequestDialog().getTag());
            cancelOkHttpCall(getRequestDialog().getTag());
        });
    }


    @Override
    public void setTag(int tag) {
        if (getRequestDialog() != null) {
            getRequestDialog().setTag(tag);
        }
    }

    @Override
    public int getTag() {
        if (getRequestDialog() != null) {
            return getRequestDialog().getTag();
        }
        return -1;
    }
}
