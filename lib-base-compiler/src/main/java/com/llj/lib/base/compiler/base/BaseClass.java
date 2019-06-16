package com.llj.lib.base.compiler.base;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-13
 */
public class BaseClass {
    private TypeElement mTypeElement;

    public BaseClass(Element element) {
        if (element.getKind() != ElementKind.CLASS) {
            throw new IllegalArgumentException(
                    String.format("Only classes can be annotated with @%s", element.getSimpleName()));
        }
        this.mTypeElement = (TypeElement) element;
    }

    public Name getMethodName() {
        return mTypeElement.getSimpleName();
    }

}
