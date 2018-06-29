package com.llj.architecturedemo;

import com.llj.lib.base.MvpBaseActivity;
import com.llj.lib.base.mvp.IPresenter;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public abstract class MyBaseActivity<P extends IPresenter> extends MvpBaseActivity<P> {
}
