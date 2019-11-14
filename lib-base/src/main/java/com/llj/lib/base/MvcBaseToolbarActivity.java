package com.llj.lib.base;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.llj.lib.base.config.ToolbarConfig;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-07-25
 */
public abstract class MvcBaseToolbarActivity extends MvcBaseActivity {
    protected ConstraintLayout mClToolbar;
    protected ImageView        mIvTbClose;
    protected TextView         mTvTbTitle;
    protected TextView         mTvTbRight;
    protected ImageView        mIvTbRight;
    protected View             mVDivide;

    @Override
    public View layoutView() {
        ViewGroup mRootView = (ViewGroup) getLayoutInflater().inflate(R.layout.mvc_base_title_activity, null);
        if (layoutId() != 0) {
            mClToolbar = mRootView.findViewById(R.id.cl_toolbar);
            mIvTbClose = mRootView.findViewById(R.id.iv_close);
            mTvTbTitle = mRootView.findViewById(R.id.tv_title);
            mTvTbRight = mRootView.findViewById(R.id.tv_right);
            mIvTbRight = mRootView.findViewById(R.id.iv_right);
            mVDivide = mRootView.findViewById(R.id.v_divide);
            initToolbar();
            getLayoutInflater().inflate(layoutId(), mRootView, true);
        }
        return mRootView;
    }

    private void initToolbar() {
        ToolbarConfig toolbarConfig = AppManager.getInstance().getToolbarConfig();
        if (toolbarConfig != null) {
            if (toolbarConfig.getToolbarBackgroundColorRes() >= 0) {
                mClToolbar.setBackgroundColor(getCompatColor(this, toolbarConfig.getToolbarBackgroundColorRes()));
            }
            if (toolbarConfig.getToolbarBackgroundDrawable() != null) {
                mClToolbar.setBackground(toolbarConfig.getToolbarBackgroundDrawable());
            }

            mIvTbClose.setImageResource(toolbarConfig.getLeftImageRes());

            mTvTbTitle.setTextSize(toolbarConfig.getTitleUnit(), toolbarConfig.getTitleSize());
            if (toolbarConfig.getTitleColorRes() >= 0) {
                mTvTbTitle.setTextColor(getCompatColor(this, toolbarConfig.getTitleColorRes()));
            }

            if (toolbarConfig.getRightImageRes() >= 0) {
                mIvTbRight.setImageResource(toolbarConfig.getRightImageRes());
            }

            mTvTbRight.setTextSize(toolbarConfig.getRightUnit(), toolbarConfig.getRightTextSize());
            if (toolbarConfig.getRightTextColorRes() > 0) {
                mTvTbRight.setTextColor(getCompatColor(this, toolbarConfig.getRightTextColorRes()));
            }

            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) mVDivide.getLayoutParams();
            layoutParams2.height = toolbarConfig.getDivideHeight();
            if (toolbarConfig.getDivideBackgroundColorRes() >= 0) {
                mVDivide.setBackgroundColor(getCompatColor(this, toolbarConfig.getDivideBackgroundColorRes()));
            }
            mVDivide.setVisibility(toolbarConfig.getVisibility());
        }

    }


}
