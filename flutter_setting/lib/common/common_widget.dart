import 'dart:math' as math;

import 'package:flutter/material.dart';
import 'package:flutter_setting/setting/application.dart';
import 'package:flutter_setting/utils/display_utils.dart';

class CommonWidget {
//  static Widget getCommonDivider(
//      double marginHorizontal, double marginVertical) {
//    return Container(
//      margin: EdgeInsets.symmetric(
//          horizontal: marginHorizontal, vertical: marginVertical),
//      height: 1,
//      color: Color(0xffeeeeee),
//    );
//  }

  static Widget getCommonDivider(double marginHorizontal) {
    return Container(
      margin: EdgeInsets.symmetric(horizontal: marginHorizontal),
      height: 1 / DisplayUtils.pixelRatio,
      color: Color(0xffeeeeee),
    );
  }
}

class CommonTitleWidgetPage extends StatelessWidget {
  String commonTitle;

  @override
  Widget build(BuildContext context) {
    return Container(
      //默认白色背景
      color: Colors.cyan,
      //设置安全区域
      child: SafeArea(
        child: Column(
          children: <Widget>[
            //CommonTitleBar
            CommonTitleBar(commonTitle),
            //中间模块
            Expanded(
              child: buildChild(context),
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
  String commonTitle;

  CommonTitleBar(this.commonTitle);

  @override
  Widget build(BuildContext context) {
    //状态栏
    EdgeInsets padding = MediaQuery
        .of(context)
        .padding;
    double top = math.max(padding.top, EdgeInsets.zero.top);

    return Container(
      alignment: Alignment.topCenter,
//      margin: EdgeInsets.only(top: top),
      constraints: BoxConstraints.expand(width: double.infinity, height: 50),
      decoration: BoxDecoration(
        color: Colors.blueGrey,
      ),
      child: ConstrainedBox(
        constraints: BoxConstraints.expand(
            width: double.infinity, height: double.infinity),
        //Stack无法设置宽高，可以使用ConstrainedBox来设置match_parent
        child: Stack(
          fit: StackFit.loose,
          alignment: Alignment.center,
          children: <Widget>[
            Positioned(
              left: 0,
              top: 0,
              bottom: 0,
              child: GestureDetector(
                child: Container(
                  padding: EdgeInsets.symmetric(horizontal: 16),
                  alignment: Alignment.center,
                  color: Colors.brown,
                  child: Image(
                    image: AssetImage("assets/images/service_icon_back.png"),
                    fit: BoxFit.scaleDown,
                  ),
                ),
                onTap: () {
                  Application.router.pop(context);
                },
              ),
            ),
            Text(
              commonTitle,
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
      ),
    );
  }
}
