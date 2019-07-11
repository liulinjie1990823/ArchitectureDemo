package com.llj.lib.jump.compiler;

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
public class JumpAnnotateClass extends BaseAnnotateClass {
    private JumpClass          mJumpClass;//@Jump
    private List<JumpKeyField> mJumpKeyFields;//@JumpKey


    public JumpAnnotateClass(Elements elementUtils) {
        super(elementUtils);
        this.mJumpKeyFields = new ArrayList<>();
    }

    @Override
    public String getFullClassName() {
        return JumpProcessor.PACKAGE + JumpProcessor.CLASS_NAME;
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

    public List<JumpKeyField> getJumpKeyFields() {
        return mJumpKeyFields;
    }

    public void addField(JumpKeyField field) {
        mJumpKeyFields.add(field);
    }

    @Override
    public JavaFile generateCode() {
        return null;
    }
}
