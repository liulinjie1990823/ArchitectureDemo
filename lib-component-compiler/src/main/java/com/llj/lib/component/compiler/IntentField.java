package com.llj.lib.component.compiler;

import com.llj.lib.component.annotation.IntentKey;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
public class IntentField {
    private VariableElement variableElement;
    private String          key;

    public IntentField(Element element) {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s", IntentKey.class.getSimpleName()));
        }
        variableElement = (VariableElement) element;
        IntentKey intent = variableElement.getAnnotation(IntentKey.class);
        key = intent.name();
    }

    public Name getFieldName() {
        return variableElement.getSimpleName();
    }

    public String getKey() {
        return key;
    }

    public TypeMirror getFieldType() {
        return variableElement.asType();
    }
}
