package com.llj.lib.component.compiler;

import com.llj.lib.component.annotation.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

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
public class BindViewField {
    private VariableElement mFieldElement;
    private int             mResId;
    private TypeMirror      mTypeMirror;
    private TypeName        mTypeName;

    public BindViewField(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(
                    String.format("Only fields can be annotated with @%s", BindView.class.getSimpleName()));
        }

        mFieldElement = (VariableElement) element;
        mTypeMirror = mFieldElement.asType();
        mTypeName = TypeName.get(mTypeMirror);
        BindView bindView = mFieldElement.getAnnotation(BindView.class);
        mResId = bindView.value();

        if (mResId < 0) {
            throw new IllegalArgumentException(
                    String.format("value() in %s for field %s is not valid !", BindView.class.getSimpleName(),
                            mFieldElement.getSimpleName()));
        }
    }

    public Name getFieldName() {
        return mFieldElement.getSimpleName();
    }

    public int getResId() {
        return mResId;
    }

    public TypeMirror getTypeMirror() {
        return mTypeMirror;
    }

    public TypeName getTypeName() {
        return mTypeName;
    }

    public ClassName getRawType() {
        if (mTypeName instanceof ParameterizedTypeName) {
            return ((ParameterizedTypeName) mTypeName).rawType;
        }
        return (ClassName) mTypeName;
    }
}
