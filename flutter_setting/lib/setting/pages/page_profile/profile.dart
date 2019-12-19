import 'package:flutter/material.dart';
import 'package:flutter_setting/common/CommonTitleWidget.dart';

class ProfilePage extends CommonTitleWidgetPage {
  final int titleColor = 0xFF333333;
  final int decColor = 0xFF666666;
  final String imageUrl2 =
      "http://n.sinaimg.cn/sports/2_img/upload/4f160954/107/w1024h683/20181128/Yrxn-hpinrya6814381.jpg";

  @override
  Widget buildChild(BuildContext context) {
    return Column(
      children: <Widget>[
        Stack(
          children: <Widget>[
            Image(
              fit: BoxFit.scaleDown,
              image: NetworkImage(imageUrl2),
            )
          ],
        ),
        setItem("昵称", "手机用户2095", titleColor, decColor),
        setItem("昵称", "手机用户2095", titleColor, decColor),
        setItem("昵称", "手机用户2095", titleColor, decColor),
      ],
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
}
