package com.llj.architecturedemo;

import android.databinding.ViewDataBinding;

import com.llj.lib.base.BaseActivity;
import com.llj.lib.base.mvp.IPresenter;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public abstract class MyBaseActivity<P extends IPresenter,B extends ViewDataBinding> extends BaseActivity<P ,B> {
}
