```
public class JumpHelp_app implements IRouteGroup {
  @Override
  public void loadInto(Map<String, JumpCallback> map) {
    map.put("ciw://EventActivity",new JumpCallback() {
      @Override
      public void process(String paramOriginStr, Map<String, String> map) {
        Postcard postcard = ARouter.getInstance().build("/app/EventActivity");
        postcard.withInt("needLogin", AParseUtils.parseInt(map.get("login")));
        postcard.navigation();
      }
    });
    map.put("ciw://AptActivity",new JumpCallback() {
      @Override
      public void process(String paramOriginStr, Map<String, String> map) {
        Postcard postcard = ARouter.getInstance().build("/app/AptActivity");
        postcard.withInt("needLogin", AParseUtils.parseInt(map.get("login")));
        postcard.navigation();
      }
    });
    map.put("ciw://AptActivity2",new JumpCallback() {
      @Override
      public void process(String paramOriginStr, Map<String, String> map) {
        if(map == null || map.get("boolean1") == null) {
          return;
        }
        Postcard postcard = ARouter.getInstance().build("/app/AptActivity2");
        if(map != null) postcard.withString("KEY_NICKNAME",map.get("name"));
        if(map != null) postcard.withBoolean("BOOLEAN",Boolean.parseBoolean(map.get("boolean1")));
        if(map != null) postcard.withShort("SHORT",AParseUtils.parseShort(map.get("short1")));
        if(map != null) postcard.withInt("INT",AParseUtils.parseInt(map.get("int1")));
        if(map != null) postcard.withLong("LONG",AParseUtils.parseLong(map.get("long1")));
        if(map != null) postcard.withFloat("FLOAT",AParseUtils.parseFloat(map.get("float1")));
        if(map != null) postcard.withDouble("DOUBLE",AParseUtils.parseDouble(map.get("double1")));
        postcard.withInt("needLogin", AParseUtils.parseInt(map.get("login")));
        postcard.withInt("needLogin", 1);
        postcard.navigation();
      }
    });
  }
}
```


## lib-jump-annotation
### Jump 页面标识
### JumpKey 字段标识

## lib-jump-api

## lib-jump-compiler
### JumpProcessor 处理类
- 统计所有的标记类，遍历所有的类写入一个文件（一个module一个文件，通过文件名来命名）

