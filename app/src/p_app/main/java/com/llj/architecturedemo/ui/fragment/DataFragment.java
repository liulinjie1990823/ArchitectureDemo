package com.llj.architecturedemo.ui.fragment;

import android.os.Bundle;

import com.llj.architecturedemo.R;
import com.llj.architecturedemo.ui.model.BabyHomeModuleItemVo;
import com.llj.lib.base.MvcBaseFragment;

import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/2
 */
public class DataFragment extends MvcBaseFragment {

    public static DataFragment getInstance(BabyHomeModuleItemVo data, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putSerializable("data", data);
        DataFragment dataFragment = new DataFragment();
        dataFragment.setArguments(bundle);
        return dataFragment;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_data;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {

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
