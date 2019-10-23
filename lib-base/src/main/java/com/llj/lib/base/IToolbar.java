package com.llj.lib.base;

import android.support.annotation.DrawableRes;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-19
 */
public interface IToolbar {

     void setToolbarHeight();

     void setDivideVisibility();

     void setDivideHeight();

     void setTitle();

     void setLeftImage(@DrawableRes int resId);
     void setRightImage(@DrawableRes int resId);
}
