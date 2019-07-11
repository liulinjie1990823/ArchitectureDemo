package com.llj.lib.base.compiler.base;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-13
 */
public abstract class BaseMethod {
    private ExecutableElement mMethodElement;

    private List<? extends VariableElement> mParameters;//参数
    private Name                            fullClassName;//全类名：com.llj.login.ui.presenter.LoginPresenter
    private Name                            simpleClassName;//简单类名：LoginPresenter

    public BaseMethod(Element element) {
        if (element.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException(
                    String.format("Only methods can be annotated with @%s", element.getSimpleName()));
        }
        this.mMethodElement = (ExecutableElement) element;
        this.mParameters = mMethodElement.getParameters();
        this.simpleClassName = mMethodElement.getEnclosingElement().getSimpleName();
        this.fullClassName = mMethodElement.getEnclosingElement().getSimpleName();
    }

    public Name getMethodName() {
        return mMethodElement.getSimpleName();
    }

    public List<? extends VariableElement> getParameters() {
        return mParameters;
    }

    public Name getFullClassName() {
        return fullClassName;
    }

    public Name getSimpleClassName() {
        return simpleClassName;
    }
}
