package com.llj.lib.utils;


import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/15.
 */

public class AFragmentUtils {

    public static void addFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment) {
        addFragment(fragmentManager, containerViewId, fragment, false);
    }

    public static void addFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment, boolean hide) {
        if (fragmentManager == null || fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerViewId, fragment);
        if (hide) {
            ft.hide(fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public static void addFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment, String tag) {
        addFragment(fragmentManager, containerViewId, fragment, tag, false);
    }

    public static void addFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment, String tag, boolean hide) {
        if (fragmentManager == null || fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerViewId, fragment, tag);
        if (hide) {
            ft.hide(fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public static void replaceFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment) {
        if (fragmentManager == null || fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerViewId, fragment)
                .commitAllowingStateLoss();
    }

    public static void replaceFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment, String tag) {
        if (fragmentManager == null || fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerViewId, fragment, tag).commitAllowingStateLoss();
    }

    public static void removeFragment(FragmentManager fragmentManager, String tag) {
        if (fragmentManager == null) {
            return;
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(fragment).commitAllowingStateLoss();
    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager == null || fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(fragment).commitAllowingStateLoss();
    }

    public static void show(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager == null || fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.show(fragment).commitAllowingStateLoss();
    }

    public static void show(FragmentManager fragmentManager, String tag) {
        if (fragmentManager == null) {
            return;
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.show(fragment).commitAllowingStateLoss();
    }

    public static void hide(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager == null || fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(fragment).commitAllowingStateLoss();
    }

    public static void hide(FragmentManager fragmentManager, String tag) {
        if (fragmentManager == null) {
            return;
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(fragment).commitAllowingStateLoss();
    }

    public static String makeFragmentTag(int tag) {
        return "fragment" + tag;
    }

    public static String makeFragmentTag(String tag) {
        return "fragment" + tag;
    }

    public static String getVpFragmentName(ViewPager viewPager, long id) {
        return "android:switcher:" + viewPager.getId() + ":" + id;
    }
}
