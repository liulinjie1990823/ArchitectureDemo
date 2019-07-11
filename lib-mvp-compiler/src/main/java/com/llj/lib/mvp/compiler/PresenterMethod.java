package com.llj.lib.mvp.compiler;

import com.llj.lib.base.compiler.base.BaseMethod;
import com.llj.lib.mvp.annotation.Presenter;

import javax.lang.model.element.Element;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-07-10
 */
public class PresenterMethod extends BaseMethod {

    private TypeMirror object;//接口返回实体对象
    private boolean    showLoading;//true使用加载框，false使用下载
    private String     method;//接口调用方法
    private String     requestIdKey;//requestId的入参变量
    private int        exceptionId;//exceptionId

    public PresenterMethod(Element element) {
        super(element);
        try {
            element.getAnnotation(Presenter.class).object();
        } catch (MirroredTypeException mte) {
            object = mte.getTypeMirror();
        }
        showLoading = element.getAnnotation(Presenter.class).showLoading();
        method = element.getAnnotation(Presenter.class).method();
        requestIdKey = element.getAnnotation(Presenter.class).requestIdKey();
        exceptionId = element.getAnnotation(Presenter.class).exceptionId();
    }

    public TypeMirror getObject() {
        return object;
    }

    public boolean isShowLoading() {
        return showLoading;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestIdKey() {
        return requestIdKey;
    }

    public int getExceptionId() {
        return exceptionId;
    }
}
