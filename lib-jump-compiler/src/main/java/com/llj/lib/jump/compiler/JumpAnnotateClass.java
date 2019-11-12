package com.llj.lib.jump.compiler;

import com.llj.lib.base.compiler.base.BaseAnnotateClass;
import com.squareup.javapoet.JavaFile;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * ArchitectureDemo.
 *
 * 自动生成外链和router跳转界面映射。
 *
 *
 * <pre>
 * {@code
 * public class JumpHelp_app implements IRouteGroup {
 *   @Override
 *   public void loadInto(Map<String, JumpCallback> map) {
 *     map.put("ciw://EventActivity",new JumpCallback() {
 *       @Override
 *       public void process(String paramOriginStr, Map<String, String> map) {
 *         Postcard postcard = ARouter.getInstance().build("/app/EventActivity");
 *         postcard.withInt("needLogin", AParseUtils.parseInt(map.get("login")));
 *         postcard.navigation();
 *       }
 *
 *       @Override
 *       public String getInPath() {
 *         return "/app/EventActivity";
 *       }
 *     });
 *     map.put("ciw://AptActivity",new JumpCallback() {
 *       @Override
 *       public void process(String paramOriginStr, Map<String, String> map) {
 *         Postcard postcard = ARouter.getInstance().build("/app/AptActivity");
 *         postcard.withInt("needLogin", AParseUtils.parseInt(map.get("login")));
 *         postcard.navigation();
 *       }
 *
 *       @Override
 *       public String getInPath() {
 *         return "/app/AptActivity";
 *       }
 *     });
 *     map.put("outPath://AptActivity2",new JumpCallback() {
 *       @Override
 *       public void process(String paramOriginStr, Map<String, String> map) {
 *         if(map == null || map.get("boolean1") == null) {
 *           return;
 *         }
 *         Postcard postcard = ARouter.getInstance().build("/app/AptActivity2");
 *         if(map != null) postcard.withString("KEY_NICKNAME",map.get("value"));
 *         if(map != null) postcard.withBoolean("BOOLEAN",Boolean.parseBoolean(map.get("boolean1")));
 *         if(map != null) postcard.withShort("SHORT",AParseUtils.parseShort(map.get("short1")));
 *         if(map != null) postcard.withInt("INT",AParseUtils.parseInt(map.get("int1")));
 *         if(map != null) postcard.withLong("LONG",AParseUtils.parseLong(map.get("long1")));
 *         if(map != null) postcard.withFloat("FLOAT",AParseUtils.parseFloat(map.get("float1")));
 *         if(map != null) postcard.withDouble("DOUBLE",AParseUtils.parseDouble(map.get("double1")));
 *         postcard.withInt("needLogin", AParseUtils.parseInt(map.get("login")));
 *         postcard.withInt("needLogin", 1);
 *         postcard.navigation();
 *       }
 *
 *       @Override
 *       public String getInPath() {
 *         return "/app/AptActivity2";
 *       }
 *     });
 *     map.put("ciw://LoadingActivity",new JumpCallback() {
 *       @Override
 *       public void process(String paramOriginStr, Map<String, String> map) {
 *         Postcard postcard = ARouter.getInstance().build("/app/LoadingActivity");
 *         postcard.withInt("needLogin", AParseUtils.parseInt(map.get("login")));
 *         postcard.withInt("needLogin", 1);
 *         postcard.navigation();
 *       }
 *
 *       @Override
 *       public String getInPath() {
 *         return "/app/LoadingActivity";
 *       }
 *     });
 *   }
 * }
 *
 * </pre>
 *
 * @author llj date 2018/9/6
 */
public class JumpAnnotateClass extends BaseAnnotateClass {

  private JumpClass          mJumpClass;//@Jump
  private List<JumpKeyField> mJumpKeyFields;//@JumpKey


  public JumpAnnotateClass(TypeElement classElement, Elements elementUtils) {
    super(classElement, elementUtils);
    this.mJumpKeyFields = new ArrayList<>();
  }

  @Override
  public String getPackageName() {
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
