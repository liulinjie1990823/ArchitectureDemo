package com.llj.lib.jump.compiler;

import com.example.lib.jump.annotation.Jump;
import com.llj.lib.base.compiler.base.BaseClass;

import javax.lang.model.element.Element;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-11
 */
public class JumpClass extends BaseClass {

    private String ciw;
    private String route;
    private String desc;
    private boolean needLogin;

    public JumpClass(Element element) {
        super(element);
        ciw = element.getAnnotation(Jump.class).ciw();
        route = element.getAnnotation(Jump.class).route();
        desc = element.getAnnotation(Jump.class).desc();
        needLogin = element.getAnnotation(Jump.class).needLogin();
    }

    public String getCiw() {
        return ciw;
    }

    public String getRoute() {
        return route;
    }

    public String getDesc() {
        return desc;
    }


    public boolean needLogin() {
        return needLogin;
    }
}
