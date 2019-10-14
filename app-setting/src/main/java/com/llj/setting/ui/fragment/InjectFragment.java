package com.llj.setting.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.component.service.arouter.CRouter;
import com.llj.setting.R;
import com.llj.setting.R2;
import com.llj.setting.SettingMvcBaseFragment;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.internal.DebouncingOnClickListener;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-10
 */

@Route(path = CRouter.SETTING_INJECT_FRAGMENT)
public class InjectFragment extends SettingMvcBaseFragment {

    @BindView(R2.id.textView) TextView mTextView;
    @BindView(R2.id.textView2) TextView mTextView2;

    @Override
    public int layoutId() {
        return R.layout.setting_inject_fragment;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        mTextView.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                mTextView.setText("");
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("");
            }
        });

        mTextView.setOnClickListener(v -> {
            mTextView.setText("mTextView");
        });

        mTextView2.setOnClickListener(v -> {
            mTextView2.setText("mTextView2");
        });

    }

    @Override
    public void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
