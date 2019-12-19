import 'dart:math' as math;

import 'package:flutter/material.dart';

class CommonTitleWidgetPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Container(
        child: Column(
          children: <Widget>[
            //CommonTitleBar
            CommonTitleBar(),
            //中间模块
            Expanded(
              child: Container(
                child: buildChild(context),
              ),
            ),
          ],
        ),
      ),
    );
  }

  //子类实现用
  Widget buildChild(BuildContext context) {
    return Container();
  }
}

class CommonTitleBar extends StatelessWidget {
  String imageUrl2 =
      "http://n.sinaimg.cn/sports/2_img/upload/4f160954/107/w1024h683/20181128/Yrxn-hpinrya6814381.jpg";

  @override
  Widget build(BuildContext context) {
    //状态栏
    EdgeInsets padding = MediaQuery.of(context).padding;
    double top = math.max(padding.top, EdgeInsets.zero.top);

    return Container(
      alignment: Alignment.topCenter,
      margin: EdgeInsets.only(top: top),
      constraints: BoxConstraints.expand(width: double.infinity, height: 50),
      decoration: BoxDecoration(
        color: Colors.amber,
      ),
      child: Stack(
        fit: StackFit.loose,
        alignment: Alignment.center,
        children: <Widget>[
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
