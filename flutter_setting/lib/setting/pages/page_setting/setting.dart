import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_setting/common/common_widget.dart';
import 'package:flutter_setting/setting/application.dart';
import 'package:flutter_setting/setting/route/routes.dart';

class Setting extends StatelessWidget {
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

class SettingPage extends CommonTitleWidgetPage {
  SettingPage() {
    commonTitle = "设置";
  }

  @override
  Widget buildChild(BuildContext context) {
    return Container(
      //灰色背景
      color: Color(0xFFF6F6F6),
      child: Column(
        children: <Widget>[
          Expanded(
            child: Container(
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
              "退出登录dadad",
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
        children: getItems(context),
      ),
    );
  }

  Widget setDivider() {
    return Container(
      height: 1,
      color: Color(0xffeeeeee),
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
                  fontWeight: FontWeight.normal,
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

  List<Widget> getItems(BuildContext context) {
    int titleColor = 0xFF333333;
    int decColor = 0xFF666666;
    return [
//      setItem("环境切换", "", 0xFF33333, 0xFF666666),
      GestureDetector(
        child: Container(
          margin: EdgeInsets.only(top: 10),
          decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.all(Radius.circular(10.0))),
          child: setItem("我的资料", "", titleColor, decColor),
        ),
        onTap: () {
          Application.router.navigateTo(context, Routes.profilePage,
              transition: TransitionType.fadeIn);
        },
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
