import 'dart:math' as math;

import 'package:flutter/material.dart';

//void main() => runApp(_widgetForRoute(window.defaultRouteName));
//void main() => runApp(Setting());

//void main() {
//  runApp(Setting());
////  if (Platform.isAndroid) {
////    // 以下两行 设置android状态栏为透明的沉浸。写在组件渲染之后，是为了在渲染后进行set赋值，覆盖状态栏，写在渲染之前MaterialApp组件会覆盖掉这个值。
////    SystemUiOverlayStyle systemUiOverlayStyle =
////        SystemUiOverlayStyle(statusBarColor: Colors.transparent);
////    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);
////  }
//}
//
//Widget _widgetForRoute(String route) {
//  switch (route) {
//    case 'setting':
//      return Setting();
//    case 'route2':
//      return Center(
//        child: Text('route: $route', textDirection: TextDirection.ltr),
//      );
//    default:
//      return Center(
//        child: Text('Unknown route: $route', textDirection: TextDirection.ltr),
//      );
//  }
//}

class Setting extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Setting Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: SettingPage(),
    );
  }
}

class SettingPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
//      color: Color(0xFFF6F6F6),
      color: Color(0xFF000000),
      child: Column(
        children: <Widget>[
          //Toolbar
          Toolbar(),
          //中间模块
          Expanded(
            child: Container(
              color: Color(0xFFF6F6F6),
              child: SingleChildScrollView(
                child: Item(),
              ),
            ),
          ),
          //退出登录
          Container(
            color: Colors.white,
            alignment: Alignment.center,
            constraints:
            BoxConstraints.expand(width: double.infinity, height: 49),
            child: Text(
              "退出登录",
              style: TextStyle(
                fontSize: 15,
                color: Color(0xFF666666),
                fontStyle: FontStyle.normal,
                fontWeight: FontWeight.w600,
                decoration: TextDecoration.none,
              ),
              textAlign: TextAlign.center,
            ),
          )
        ],
      ),
    );
  }
}

class Item extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 10),
      child: Column(
        children: getItems(),
      ),
    );
  }

  Widget setDivider() {
    return Container(
      height: 1,
      color: Color(0xffeeeeee),
    );
  }

  Widget setDivider2() {
    return Container(
      height: 10,
      color: Color(0xffF5F8FA),
    );
  }

  Widget setItem(String title, String dec, int titleColor, int decColor) {
    return Container(
      height: 50,
      padding: EdgeInsets.symmetric(horizontal: 10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          //标题
          Expanded(
            child: Container(
              child: Text(
                title,
                style: TextStyle(
                  fontSize: 15,
                  color: Color(titleColor),
                  fontStyle: FontStyle.normal,
                  fontWeight: FontWeight.w600,
                  decoration: TextDecoration.none,
                ),
                textAlign: TextAlign.left,
              ),
            ),
          ),
          //内容
          Text(
            dec,
            softWrap: true,
            maxLines: 10,
            style: TextStyle(
              fontSize: 15,
              color: Color(decColor),
              fontStyle: FontStyle.normal,
              fontWeight: FontWeight.normal,
              decoration: TextDecoration.none,
            ),
            overflow: TextOverflow.ellipsis,
            textAlign: TextAlign.center,
          ),
          //箭头
          Container(
            child: Image(
              fit: BoxFit.scaleDown,
              image: AssetImage("assets/images/service_icon_right_arrow.png"),
            ),
          ),
        ],
      ),
    );
  }

  Widget wrapItems() {}

  List<Widget> getItems() {
    int titleColor = 0xFF333333;
    int decColor = 0xFF666666;
    return [
//      setItem("环境切换", "", 0xFF33333, 0xFF666666),
      Container(
        margin: EdgeInsets.only(top: 10),
        decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.all(Radius.circular(10.0))),
        child: setItem("我的资料", "", titleColor, decColor),
      ),
      Container(
        margin: EdgeInsets.only(top: 10),
        decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.all(Radius.circular(10.0))),
        child: Column(
          children: <Widget>[
            setItem("绑定支付宝", "", titleColor, decColor),
            setDivider(),
            setItem("绑定微信", "", titleColor, decColor),
            setDivider(),
            setItem("推送开关", "", titleColor, decColor),
            setDivider(),
            setItem("清除缓存", "1000KB", titleColor, decColor),
            setDivider(),
            setItem("意见反馈", "", titleColor, decColor),
          ],
        ),
      ),
      Container(
        margin: EdgeInsets.only(top: 10),
        decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.all(Radius.circular(10.0))),
        child: Column(
          children: <Widget>[
            setItem("关于中国婚博会", "v6.12.0", titleColor, decColor),
            setDivider(),
            setItem("隐私政策", "", titleColor, decColor),
            setDivider(),
            setItem("推荐给好友", "", titleColor, decColor),
            setDivider(),
            setItem("官方微信", "", titleColor, decColor),
            setDivider(),
            setItem("联系客服", "400-365-520", titleColor, decColor),
          ],
        ),
      ),
      Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Text("hi"),
          Text("world"),
          Text("world"),
          Text("world"),
          Text("world"),
          Text("world"),
          Text("world"),
          Text("world"),
        ],
      ),
    ];
  }
}

class Toolbar extends StatelessWidget {
  String imageUrl2 =
      "http://n.sinaimg.cn/sports/2_img/upload/4f160954/107/w1024h683/20181128/Yrxn-hpinrya6814381.jpg";

  @override
  Widget build(BuildContext context) {
    //状态栏
    EdgeInsets padding = MediaQuery
        .of(context)
        .padding;
    double top = math.max(padding.top, EdgeInsets.zero.top);

    return Container(
      margin: EdgeInsets.only(top: top),
      constraints: BoxConstraints.expand(width: double.infinity, height: 50),
      decoration: BoxDecoration(
        color: Colors.amber,
      ),
      child: Stack(
        fit: StackFit.loose,
        alignment: Alignment.center,
        children: <Widget>[
//          Positioned(
//            left: 0,
//            child: Container(
//              padding: EdgeInsets.symmetric(horizontal: 8),
//              alignment: Alignment.centerLeft,
//              width: 100.0,
//              height: 49.0,
//              decoration: BoxDecoration(
//                color: Colors.deepOrangeAccent,
//                image: DecorationImage(
//                  image: AssetImage("assets/images/service_icon_back.png"),
//                  fit: BoxFit.scaleDown,
//                ),
//              ),
////              child: Image(
////                image: AssetImage("assets/images/service_icon_back.png"),
////                fit: BoxFit.scaleDown,
////              ),
//            ),
//          ),

          Positioned(
            left: 0,
            top: 0,
            bottom: 0,
            child: Container(
              padding: EdgeInsets.symmetric(horizontal: 16),
              alignment: Alignment.center,
              color: Colors.brown,
              child: Image(
                image: AssetImage("assets/images/service_icon_back.png"),
                fit: BoxFit.scaleDown,
              ),
            ),
          ),
          Text(
            "设置",
            style: TextStyle(
              color: Colors.white,
              fontSize: 18,
              fontStyle: FontStyle.normal,
              fontWeight: FontWeight.normal,
              decoration: TextDecoration.none,
            ),
            textAlign: TextAlign.center,
            maxLines: 1,
            overflow: TextOverflow.ellipsis,
          ),
          Positioned(
            right: 0,
            top: 0,
            bottom: 0,
            child: Container(
              width: 50,
              alignment: Alignment.center,
              color: Colors.blueGrey,
              child: Image(
                image: NetworkImage(imageUrl2),
                fit: BoxFit.scaleDown,
              ),
            ),
          )
        ],
      ),
    );
  }
}
