package com.llj.lib.base.compiler;

import com.llj.lib.base.compiler.base.BaseAnnotateClass;
import com.squareup.javapoet.ClassName;
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
public class JumpAnnotateClass extends BaseAnnotateClass {
    public static final String PACKAGE = "com.llj.jump";

    private static final ClassName ANDROID_VIEW              = ClassName.get("android.view", "View");
    private static final ClassName ANDROID_ON_CLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    private static final ClassName FINDER                    = ClassName.get("com.llj.lib.component.api.finder", "Finder");
    private static final ClassName PROVIDER                  = ClassName.get("com.llj.lib.component.api.provider", "Provider");
    private static final ClassName UTILS                     = ClassName.get("com.llj.lib.component.api.finder", "Utils");

    private JumpClass           mJumpClass;
    private List<InnerKeyField> mInnerKeyFields;


    public JumpAnnotateClass(Elements elementUtils) {
        super(elementUtils);
        this.mInnerKeyFields = new ArrayList<>();
    }

    @Override
    public String getFullClassName() {
        return PACKAGE + JumpProcessor.CLASS_NAME;
    }

    @Override
    public String getSimpleClassName() {
        return JumpProcessor.CLASS_NAME;
    }

    public void setJumpClass(JumpClass jumpClass) {
        mJumpClass = jumpClass;
    }

    public JumpClass getJumpClass() {
        return mJumpClass;
    }

    public List<InnerKeyField> getInnerKeyFields() {
        return mInnerKeyFields;
    }

    public void addField(InnerKeyField field) {
        mInnerKeyFields.add(field);
    }

    @Override
    public JavaFile generateCode() {
        System.out.println("----- start ---- generateInnerJump----------");
        System.out.println("----- end ---- generateInnerJump----------");
        return null;
    }
}
