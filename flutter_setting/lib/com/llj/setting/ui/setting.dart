import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Color(0xFFF6F6F6),
      child: Column(
        children: <Widget>[
          Toolbar(),
          Expanded(
            child: SingleChildScrollView(
              child: Item(),
            ),
          ),
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
    return Container(
      margin: EdgeInsets.fromLTRB(0, 20, 0, 0),
      constraints: BoxConstraints.expand(width: double.infinity, height: 89),
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
            child: Container(
//              padding: EdgeInsets.symmetric(horizontal: 16),
//              alignment: Alignment.center,
              height: 100,
              color: Colors.brown,
              child: Image(
                image: AssetImage("assets/images/mv_ic_template_free.png"),
                fit: BoxFit.scaleDown,
              ),
//              child: FloatingActionButton(
//                elevation: 2,
//                highlightElevation: 5,
//                backgroundColor: Color(0xff59c2ff),
//                child: Icon(
//                  Icons.add,
//                  size: 38,
//                ),
//                onPressed: () {},
//              ),
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
            child: Container(
              width: 49.0,
              height: 49.0,
              decoration: BoxDecoration(
                color: Colors.blueGrey,
                image: DecorationImage(
                  image: NetworkImage(imageUrl2),
                  fit: BoxFit.scaleDown,
                ),
              ),
            ),
          )
        ],
      ),
    );
  }
}
