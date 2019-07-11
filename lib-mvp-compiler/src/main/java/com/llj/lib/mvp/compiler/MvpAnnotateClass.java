package com.llj.lib.mvp.compiler;

import com.llj.lib.base.compiler.base.BaseAnnotateClass;
import com.squareup.javapoet.JavaFile;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.Elements;

/**
 * ArchitectureDemo.
 * describe:generate Jump Code
 * author llj
 * date 2018/9/6
 */
public class MvpAnnotateClass extends BaseAnnotateClass {

    private List<IViewMethod> mIViewMethods;//@IView

    public MvpAnnotateClass(Elements elementUtils) {
        super(elementUtils);
        mIViewMethods = new ArrayList<>();
    }

    @Override
    public String getFullClassName() {
        return "";
    }

    @Override
    public String getSimpleClassName() {
        return "";
    }

    public void addIViewMethod(IViewMethod method) {
        mIViewMethods.add(method);
    }

    @Override
    public JavaFile generateCode() {
        System.out.println("----- start ---- generateInnerJump----------");

        for (int i = 0; i < mIViewMethods.size(); i++) {
            IViewMethod iViewMethod = mIViewMethods.get(i);
            if (iViewMethod == null) {
                continue;
            }

        }

        System.out.println("----- end ---- generateInnerJump----------");
        return null;
    }
}
