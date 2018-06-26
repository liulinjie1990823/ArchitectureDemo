package com.llj.lib.base.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;

import com.facebook.stetho.common.LogUtil;
import com.llj.lib.base.BaseDialog;
import com.llj.lib.base.R;
import com.llj.lib.net.observer.ITag;
import com.llj.lib.utils.ADisplayUtils;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public class LoadingDialog extends BaseDialog implements ITag {
    private int mTag = -1;

    public LoadingDialog(Context context) {
        super(context, R.style.no_dim_dialog);
    }

    @Override
    public int layoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void initViews() {

    }

    @Override
    protected void setWindowParam() {
        int width = ADisplayUtils.dp2px(getContext(), 120);
        setWindowParams(width, width, Gravity.CENTER);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void performCreate(Bundle savedInstanceState) {
        super.performCreate(savedInstanceState);

        if (mTag <= 0) {
            throw new RuntimeException("请先添加mTag");
        }

        setOnCancelListener(dialog -> {
            LogUtil.i(TAG_LOG, "cancelOkHttpCall:" + getRequestTag());
            cancelOkHttpCall(getRequestTag());
        });
    }

    @Override
    public boolean needLoadingDialog() {
        return false;
    }


    @Override
    public void setRequestTag(int tag) {
        mTag = tag;
    }

    @Override
    public int getRequestTag() {
        return mTag;
    }

    @Override
    public void showLoadingDialog() {
        show();
    }

    @Override
    public void dismissLoadingDialog() {
        dismiss();
    }
}
