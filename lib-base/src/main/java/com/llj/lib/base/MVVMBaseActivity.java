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

import com.llj.lib.base.mvvm.BaseViewModel;
import com.llj.lib.base.widget.LoadingDialog;
import com.llj.lib.net.observer.ITag;
import com.llj.lib.utils.AInputMethodManagerUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/30
 */
public abstract class MVVMBaseActivity<VM extends BaseViewModel, B extends ViewDataBinding> extends AppCompatActivity
        implements IBaseActivity, ICommon, IUiHandler, IEvent, ILoadingDialogHandler<Disposable> {
    public String TAG_LOG;

    @Inject
    protected VM mViewModel;
    protected B  mDataBinding;

    protected ITag mRequestDialog;

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
        Dialog requestDialog = (Dialog) getLoadingDialog();
        if (requestDialog.isShowing()) {
            requestDialog.cancel();
        }

        //注销事件总线
        unregister(this);

        //移除列表中的activity
        removeCurrentActivity(this);
    }
    //</editor-fold >

    //<editor-fold desc="事件总线">
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
    }
    //</editor-fold >

    //<editor-fold desc="IBaseActivity">
    @Override
    public void initLifecycleObserver(@NonNull Lifecycle lifecycle) {
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

    //<editor-fold desc="ILoadingDialogHandler">
    @Override
    public ITag getLoadingDialog() {
        return mRequestDialog;
    }

    @Override
    public ITag initLoadingDialog() {
        return null;
    }

    public void checkRequestDialog() {
        if (mRequestDialog == null) {
            mRequestDialog = initLoadingDialog();
            if (mRequestDialog == null) {
                mRequestDialog = new LoadingDialog(this);
            }
        }
        setRequestTag(hashCode());
    }

    //如果该RequestDialog和请求关联就设置tag
    @Override
    public void setRequestTag(int tag) {
        if (getLoadingDialog() != null) {
            getLoadingDialog().setRequestTag(tag);
        }
    }

    @Override
    public int getRequestTag() {
        if (getLoadingDialog() != null) {
            return getLoadingDialog().getRequestTag();
        }
        return -1;
    }
    //</editor-fold >

    //<editor-fold desc="处理点击外部影藏输入法">
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AInputMethodManagerUtils.hideSoftInputFromWindow(this);
        }
        return super.onTouchEvent(event);
    }
    //</editor-fold >

}
