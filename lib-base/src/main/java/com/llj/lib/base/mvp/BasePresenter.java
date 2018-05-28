package com.llj.lib.base.mvp;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.llj.lib.base.BaseEvent;
import com.llj.lib.base.utils.Preconditions;
import com.llj.lib.utils.LogUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

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
    private final String TAG = this.getClass().getSimpleName();

    private CompositeDisposable mCompositeDisposable;
    private LifecycleOwner      mLifecycleOwner;

    protected M mModel;
    protected V mView;

    public BasePresenter(M model, V view) {
        Preconditions.checkNotNull(model, "%s cannot be null", IModel.class.getName());
        Preconditions.checkNotNull(view, "%s cannot be null", IView.class.getName());
        this.mModel = model;
        this.mView = view;
        start();
    }

    public BasePresenter(V view) {
        Preconditions.checkNotNull(view, "%s cannot be null", IView.class.getName());
        this.mView = view;
        start();
    }

    public BasePresenter() {
        start();
    }


    private void start() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mView != null && mView instanceof LifecycleOwner) {
            ((LifecycleOwner) mView).getLifecycle().addObserver(this);
            if (mModel != null && mModel instanceof LifecycleObserver) {
                ((LifecycleOwner) mView).getLifecycle().addObserver((LifecycleObserver) mModel);
            }
        }
        register(this);
    }

    private void destroy() {
        unregister(this);

        unDispose();//解除订阅

        if (mModel != null) {
            mModel.destroy();
        }
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
    }


    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, "BasePresenter onCreate" + owner.getLifecycle().getCurrentState());
        mLifecycleOwner = owner;
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, "BasePresenter onStart" + owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, "BasePresenter onResume" + owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, "BasePresenter onPause" + owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, "BasePresenter onStop" + owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, "BasePresenter onDestroy" + owner.getLifecycle().getCurrentState());
        destroy();
        owner.getLifecycle().removeObserver(this);
    }

    @Override
    public <T> AutoDisposeConverter<T> bindLifecycle() {
        if (null == mLifecycleOwner)
            throw new NullPointerException("lifecycleOwner == null");
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(mLifecycleOwner));
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
