package com.llj.lib.base;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llj.lib.base.mvp.IPresenter;
import com.llj.lib.base.widget.LoadingDialog;
import com.llj.lib.net.observer.ITag;
import com.llj.lib.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public abstract class MvpBaseFragment<P extends IPresenter> extends Fragment
        implements IFragment, IFragmentLazy, ICommon, IUiHandler, ILoadingDialogHandler {

    public String  TAG_LOG;
    public Context mContext;

    private boolean mIsInit;
    private boolean mIsVisible;

    @Inject
    protected P        mPresenter;
    private   Unbinder mUnbinder;

    protected ITag mRequestDialog;

    //<editor-fold desc="生命周期">
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        View layoutView = layoutView();
        if (layoutView != null) {
            view = layoutView;
        } else {
            view = inflater.inflate(layoutId(), null);
        }
        mUnbinder = ButterKnife.bind(this, view);

        checkRequestDialog();

        getArgumentsData(getArguments());

        initViews(savedInstanceState);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //当fragment在viewPager中的时候需要实现懒加载的模式
        //当使用viewPager进行预加载fragment的时候,先调用setUserVisibleHint,后调用onViewCreated
        //所以刚开始是mIsInit=true,mIsVisible为false
        if (hasInitAndVisible()) {
            onLazyLoad();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 已经完成初始化
        mIsInit = true;
        //
        initViews(savedInstanceState);
        //
        if (useLazyLoad()) {
            if (hasInitAndVisible()) {
                onLazyLoad();
            }
        } else {
            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //防止窗口泄漏
        Dialog requestDialog = (Dialog) getLoadingDialog();
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
    }
    //</editor-fold >

    //<editor-fold desc="IFragment">
    @Override
    public void initLifecycleObserver(@NonNull Lifecycle lifecycle) {
        //将mPresenter作为生命周期观察者添加到lifecycle中
        if (mPresenter != null) {
            lifecycle.addObserver(mPresenter);
        }
    }
    //</editor-fold >

    //<editor-fold desc="IFragmentLazy">
    @Override
    public boolean hasInitAndVisible() {
        return mIsInit && getUserVisibleHint();
    }

    @Override
    public void onLazyLoad() {
        LogUtil.e(TAG_LOG, "mIsInit:" + mIsInit + ",mIsVisible:" + mIsVisible);
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
        }
        if (mRequestDialog == null) {
            mRequestDialog = new LoadingDialog(mContext);
        }
        setRequestTag(hashCode());
    }


    @Override
    public void setRequestTag(@NotNull Object tag) {
        if (getLoadingDialog() != null) {
            getLoadingDialog().setRequestTag(tag);
        }
    }

    @Override
    public Object getRequestTag() {
        if (getLoadingDialog() != null) {
            return getLoadingDialog().getRequestTag();
        }
        return -1;
    }
    //</editor-fold >
}
