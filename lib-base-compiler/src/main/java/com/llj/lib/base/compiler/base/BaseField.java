package com.llj.lib.base.compiler.base;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-13
 */
public abstract class BaseField {
    private VariableElement mVariableElement;

    public BaseField(Element element) {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(
                    String.format("Only fields can be annotated with @%s", element.getSimpleName()));
        }
        mVariableElement = (VariableElement) element;
    }

    public Name getSimpleName() {
        return mVariableElement.getSimpleName();
    }

    public Element getElement() {
        return mVariableElement;
    }

    public TypeMirror getTypeMirror() {
        return mVariableElement.asType();
    }

    public TypeKind getTypeKind() {
        return mVariableElement.asType().getKind();
    }
}
