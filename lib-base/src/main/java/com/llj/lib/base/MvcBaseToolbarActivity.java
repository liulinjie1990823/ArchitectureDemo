package com.llj.lib.base;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-07-25
 */
public abstract class MvcBaseToolbarActivity extends MvcBaseActivity {
    private   ViewGroup        mRootView;
    protected ConstraintLayout mClToolbar;
    protected ImageView        mIvTbClose;
    protected TextView         mTvTbTitle;
    protected TextView         mTvTbRight;
    protected ImageView        mIvTbRight;

    @Override
    public View layoutView() {
        if (layoutId() != 0) {
            mRootView = (ViewGroup) getLayoutInflater().inflate(R.layout.mvc_base_title_activity, null);
            mClToolbar = mRootView.findViewById(R.id.cl_toolbar);
            mIvTbClose = mRootView.findViewById(R.id.iv_close);
            mTvTbTitle = mRootView.findViewById(R.id.tv_title);
            mTvTbRight = mRootView.findViewById(R.id.tv_right);
            mIvTbRight = mRootView.findViewById(R.id.iv_right);
            getLayoutInflater().inflate(layoutId(), mRootView, true);
        }
        return mRootView;
    }
}
