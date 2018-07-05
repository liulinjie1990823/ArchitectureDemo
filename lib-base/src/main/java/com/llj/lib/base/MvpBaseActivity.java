package com.llj.lib.base;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.stetho.common.LogUtil;
import com.llj.lib.base.mvp.IPresenter;
import com.llj.lib.base.widget.LoadingDialog;
import com.llj.lib.net.observer.ITag;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Set;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/15
 */
public abstract class MvpBaseActivity<P extends IPresenter> extends AppCompatActivity
        implements IBaseActivity, ICommon, IUiHandler, IEvent, ILoadingDialogHandler, ITask {
    public String TAG;

    public Context mContext;

    private ArrayMap<Integer, Disposable> mCancelableTask;

    @Inject
    protected P        mPresenter;
    protected Unbinder mUnbinder;

    protected ITag mRequestDialog;

    //<editor-fold desc="生命周期">
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;

        try {
            AndroidInjection.inject(this);
        } catch (Exception e) {

        }

        mCancelableTask = new ArrayMap<>();

        super.onCreate(savedInstanceState);

        addCurrentActivity(this);

        TAG = getClass().getSimpleName();

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

        //移除所有的任务
        removeAllDisposable();

        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        mUnbinder = null;

        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }

        //移除列表中的activity
        removeCurrentActivity(this);
    }
    //</editor-fold >

    //<editor-fold desc="任务处理">
    @Override
    public void addDisposable(int tag, Disposable disposable) {
        mCancelableTask.put(tag, disposable);
    }

    @Override
    public void removeDisposable(int tag) {
        Disposable disposable = mCancelableTask.get(tag);
        if (!disposable.isDisposed()) {
            disposable.dispose();
            mCancelableTask.remove(tag);
        }
    }

    @Override
    public void removeAllDisposable() {
        if (mCancelableTask.isEmpty()) {
            return;
        }
        Set<Integer> keys = mCancelableTask.keySet();
        for (Integer apiKey : keys) {
            removeDisposable(apiKey);
        }
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
            ((Dialog) mRequestDialog).setOnCancelListener(dialog -> {
                LogUtil.i(TAG, "cancelTask:" + mRequestDialog.getRequestTag());
                removeDisposable(mRequestDialog.getRequestTag());
            });
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
        return onTouchEvent(this, event);
    }
    //</editor-fold >

}
