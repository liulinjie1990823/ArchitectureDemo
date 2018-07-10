package com.llj.architecturedemo;

import com.llj.lib.base.MvpBaseActivity;
import com.llj.lib.base.mvp.IPresenter;

import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public abstract class MyBaseActivity<P extends IPresenter> extends MvpBaseActivity<P> {

    @Override
    public void accept(Disposable o) {
        showLoadingDialog();
    }
}
