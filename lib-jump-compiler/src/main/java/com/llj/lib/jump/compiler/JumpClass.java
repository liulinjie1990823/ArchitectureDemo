package com.llj.lib.jump.compiler;

import com.llj.lib.jump.annotation.Jump;
import com.llj.lib.base.compiler.base.BaseClass;

import javax.lang.model.element.Element;

/**
 * ArchitectureDemo.
 *
 * describe  类注释信息
 *
 * @author llj
 * @date 2019-06-11
 */
public class JumpClass extends BaseClass {

  private String  outPath;
  private String  inPath;
  private String  desc;
  private boolean needLogin;

  public JumpClass(Element element) {
    super(element);
    outPath = element.getAnnotation(Jump.class).outPath();
    inPath = element.getAnnotation(Jump.class).inPath();
    desc = element.getAnnotation(Jump.class).desc();
    needLogin = element.getAnnotation(Jump.class).needLogin();
  }

  public String getOutPath() {
    return outPath;
  }

  public String getInPath() {
    return inPath;
  }

  public String getDesc() {
    return desc;
  }


  public boolean needLogin() {
    return needLogin;
  }
}
