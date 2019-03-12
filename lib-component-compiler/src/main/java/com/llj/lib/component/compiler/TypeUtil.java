package com.llj.lib.component.compiler;

import com.squareup.javapoet.ClassName;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
public class TypeUtil {
    public static final ClassName ANDROID_VIEW              = ClassName.get("android.view", "View");
    public static final ClassName ANDROID_ON_CLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    public static final ClassName FINDER                    = ClassName.get("com.llj.lib.component.api.finder", "Finder");
    public static final ClassName PROVIDER                  = ClassName.get("com.llj.lib.component.api.provider", "Provider");
}
