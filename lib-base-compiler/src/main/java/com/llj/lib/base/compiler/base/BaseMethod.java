package com.llj.lib.base.compiler.base;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-13
 */
public abstract class BaseMethod {
    private ExecutableElement mMethodElement;

    public BaseMethod(Element element) {
        if (element.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException(
                    String.format("Only methods can be annotated with @%s", element.getSimpleName()));
        }
        this.mMethodElement = (ExecutableElement) element;
    }

    public  Name getMethodName(){
       return mMethodElement.getSimpleName();
    }
}
