package com.llj.lib.base.widget;

import android.content.Context;
import android.view.Gravity;

import com.llj.lib.base.BaseDialog;
import com.llj.lib.base.R;
import com.llj.lib.net.IRequestDialog;
import com.llj.lib.utils.ADisplayUtils;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public class LoadingDialog extends BaseDialog implements IRequestDialog {
    private int mTag;

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
    public boolean needLoadingDialog() {
        return false;
    }


    @Override
    public void setTag(int tag) {
        mTag = tag;
    }

    @Override
    public int getTag() {
        return mTag;
    }
}
