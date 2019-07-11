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



    public BaseAnnotateClass(TypeElement classElement, Elements elementUtils) {
        mClassElement = classElement;
        mElementUtils = elementUtils;
    }

    public String getPackageName() {
        return mElementUtils.getPackageOf(mClassElement).getQualifiedName().toString();
    }

    public String getSimpleClassName() {
        return mClassElement.getSimpleName().toString();
    }

    public abstract JavaFile generateCode();
}
