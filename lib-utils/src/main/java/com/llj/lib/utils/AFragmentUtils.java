package com.llj.lib.utils;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/15.
 */

public class AFragmentUtils {

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(fragment).commitAllowingStateLoss();
    }

    public static void addFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerViewId, fragment).commitAllowingStateLoss();
    }

    public static void replaceFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment).commitAllowingStateLoss();
    }

    public static void removeFragment(FragmentManager fragmentManager, String tag) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.remove(fragment).commitAllowingStateLoss();
        }
    }

    public static String makeFragmentTag(int tag) {
        return "fragment" + tag;
    }

    public static String makeFragmentTag(String tag) {
        return "fragment" + tag;
    }

}
