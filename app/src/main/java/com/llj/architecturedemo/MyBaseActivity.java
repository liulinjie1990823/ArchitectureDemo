package com.llj.architecturedemo;

import com.llj.lib.base.BaseActivity;
import com.llj.lib.base.mvp.IPresenter;
import com.llj.lib.base.widget.LoadingDialog;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public abstract class MyBaseActivity<P extends IPresenter> extends BaseActivity<P ,LoadingDialog> {
}
