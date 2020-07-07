package com.llj.lib.jump.compiler;

import com.llj.lib.jump.annotation.JumpKey;
import com.llj.lib.base.compiler.base.BaseField;

import javax.lang.model.element.Element;

/**
 * ArchitectureDemo.
 * describe:JumpKey
 *
 * @author llj
 * @date 2019-06-13
 */
public class JumpKeyField extends BaseField {
    private String outKey;
    private String inKey;
    private boolean required;

    public JumpKeyField(Element element) {
        super(element);
        outKey = element.getAnnotation(JumpKey.class).outKey();
        inKey = element.getAnnotation(JumpKey.class).inKey();
        required = element.getAnnotation(JumpKey.class).required();
    }

    public String getOutKey() {
        return outKey;
    }

    public String getInKey() {
        return inKey;
    }

    public boolean isRequired() {
        return required;
    }
}
