package com.llj.lib.base.mvp;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.llj.lib.base.utils.Preconditions;
import com.llj.lib.utils.LogUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
public class BasePresenter<R extends BaseRepository, V extends IView> implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();

    private CompositeDisposable mCompositeDisposable;

    protected R mRepository;
    protected V mView;

    public BasePresenter(R repository, V view) {
        Preconditions.checkNotNull(repository, "%s cannot be null", BaseRepository.class.getName());
        Preconditions.checkNotNull(view, "%s cannot be null", IView.class.getName());
        this.mRepository = repository;
        this.mView = view;
        init();
    }

    public BasePresenter(V view) {
        Preconditions.checkNotNull(view, "%s cannot be null", IView.class.getName());
        this.mView = view;
        init();
    }

    public BasePresenter() {
        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

        unDispose();//解除订阅

        this.mView = null;
        this.mCompositeDisposable = null;
    }


    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, TAG + " onCreate()" + "state:" + owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, TAG + " onStart()" + "state:" + owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, TAG + " onResume()" + "state:" + owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, TAG + " onPause()" + "state:" + owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, TAG + " onStop()" + "state:" + owner.getLifecycle().getCurrentState());
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, TAG + " onDestroy()" + "state:" + owner.getLifecycle().getCurrentState());
        destroy();
        owner.getLifecycle().removeObserver(this);
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
