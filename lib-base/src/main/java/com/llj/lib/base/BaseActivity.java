package com.llj.lib.base;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/15
 */
public abstract class BaseActivity<P extends IPresenter, B extends ViewDataBinding> extends AppCompatActivity
        implements IBaseActivity, ICommon, IUiHandler,IEvent, IRequestDialogHandler {
    public String TAG_LOG;

    @Inject
    protected P mPresenter;
    protected B mDataBinding;

    protected IRequestDialog mRequestDialog;

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
            mDataBinding = DataBindingUtil.setContentView(this, layoutId());
        } else {
            setContentView(layoutView);
            mDataBinding = DataBindingUtil.inflate(getLayoutInflater(), layoutId(), null, false);
        }

        checkRequestDialog();

        initLifecycleObserver(getLifecycle());

        register(this);

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

        //注销事件总线
        unregister(this);

        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }

        //移除列表中的activity
        removeCurrentActivity(this);
    }
    //</editor-fold >

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
    }

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
    public void backToLauncher(boolean nonRoot) {
        moveTaskToBack(nonRoot);
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
            mRequestDialog = initRequestDialog();
            if (mRequestDialog == null) {
                mRequestDialog = new LoadingDialog(this);
            }
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
