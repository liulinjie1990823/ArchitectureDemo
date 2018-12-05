package com.llj.architecturedemo.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/12/1
 */
public class HomeGoodsPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentMvcs;
    private List<String>   mTabList;

    public HomeGoodsPagerAdapter(FragmentManager fm, List<Fragment> fragmentMvcs, List<String> tabList) {
        super(fm);
        mFragmentMvcs = fragmentMvcs;
        mTabList = tabList;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentMvcs.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentMvcs.size();
    }
}
