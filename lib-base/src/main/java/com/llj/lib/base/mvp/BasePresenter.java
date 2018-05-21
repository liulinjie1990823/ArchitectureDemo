package com.llj.lib.base.mvp;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.llj.lib.base.BaseEvent;
import com.llj.lib.base.utils.Preconditions;
import com.llj.lib.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
public class BasePresenter<V extends IView, M extends IModel> implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();

    protected CompositeDisposable mCompositeDisposable;

    protected M mModel;
    protected V mRootView;

    public BasePresenter(M model, V rootView) {
        Preconditions.checkNotNull(model, "%s cannot be null", IModel.class.getName());
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mModel = model;
        this.mRootView = rootView;
        start();
    }

    public BasePresenter(V rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mRootView = rootView;
        start();
    }

    public BasePresenter() {
        start();
    }


    public void start() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mRootView != null && mRootView instanceof LifecycleOwner) {
            ((LifecycleOwner) mRootView).getLifecycle().addObserver(this);
            if (mModel != null && mModel instanceof LifecycleObserver) {
                ((LifecycleOwner) mRootView).getLifecycle().addObserver((LifecycleObserver) mModel);
            }
        }
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void destroy() {
        if (useEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        unDispose();//解除订阅

        if (mModel != null) {
            mModel.destroy();
        }
        this.mModel = null;
        this.mRootView = null;
        this.mCompositeDisposable = null;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
    }


    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG,"BasePresenter onCreate"+owner.getLifecycle().getCurrentState());

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG,"BasePresenter onStart"+owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG,"BasePresenter onResume"+owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG,"BasePresenter onPause"+owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG,"BasePresenter onStop"+owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG,"BasePresenter onDestroy"+owner.getLifecycle().getCurrentState());
        destroy();
        owner.getLifecycle().removeObserver(this);
    }

    /**
     * 是否使用 {@link EventBus},默认为使用(true)，
     *
     * @return
     */
    public boolean useEventBus() {
        return true;
    }


    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入集中处理
    }

    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }


}
