package com.llj.lib.base.mvp;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.llj.lib.base.utils.Preconditions;
import com.llj.lib.utils.LogUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
public class BasePresenter<M extends BaseViewModel, V extends IView> implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();

    private CompositeDisposable mCompositeDisposable;

    protected M mViewModel;
    protected V mView;

    public BasePresenter(M viewModel, V view) {
        Preconditions.checkNotNull(viewModel, "%s cannot be null", IModel.class.getName());
        Preconditions.checkNotNull(view, "%s cannot be null", IView.class.getName());
        this.mViewModel = viewModel;
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

        if (mViewModel != null) {
            mViewModel.destroy();
        }
        this.mViewModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }


    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        LogUtil.e(TAG, "BasePresenter onCreate" + owner.getLifecycle().getCurrentState());
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
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(mView.getLifecycle()));
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
