package com.llj.lib.base.compiler.base;

import com.squareup.javapoet.JavaFile;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-13
 */
public abstract class BaseAnnotateClass {

    public TypeElement mClassElement;
    public Elements    mElementUtils;


    public BaseAnnotateClass(Elements elementUtils) {
        mElementUtils = elementUtils;
    }

    public BaseAnnotateClass(TypeElement classElement, Elements elementUtils) {
        mClassElement = classElement;
        mElementUtils = elementUtils;
    }

    public abstract String getFullClassName();

    public abstract String getSimpleClassName();

    public abstract JavaFile generateCode();
}
