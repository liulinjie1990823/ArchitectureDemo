package com.llj.lib.jump.compiler;

import com.example.lib.jump.annotation.JumpKey;
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
    private String ciw;//
    private String route;//
    private boolean required;//

    public JumpKeyField(Element element) {
        super(element);
        route = element.getAnnotation(JumpKey.class).name();
        ciw = element.getAnnotation(JumpKey.class).ciw();
        required = element.getAnnotation(JumpKey.class).required();
    }

    public String getRoute() {
        return route;
    }

    public String getCiw() {
        return ciw;
    }

    public boolean isRequired() {
        return required;
    }
}
