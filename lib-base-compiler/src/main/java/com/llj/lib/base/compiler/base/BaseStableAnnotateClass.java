package com.llj.lib.base.compiler.base;

import com.squareup.javapoet.JavaFile;

import javax.lang.model.util.Elements;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-13
 */
public abstract class BaseStableAnnotateClass {

    public Elements    mElementUtils;

    public BaseStableAnnotateClass( Elements elementUtils) {
        mElementUtils = elementUtils;
    }

    public abstract String getFullClassName();
    public abstract String getSimpleClassName();


    public abstract JavaFile generateCode();
}
