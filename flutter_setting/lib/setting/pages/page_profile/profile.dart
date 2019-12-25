import 'package:flutter/material.dart';
import 'package:flutter_setting/common/common_widget.dart';

class ProfilePage extends CommonTitleWidgetPage {
  final int titleColor = 0xFF666666;
  final int decColor = 0xFF999999;
  final String imageUrl2 =
      "http://n.sinaimg.cn/sports/2_img/upload/4f160954/107/w1024h683/20181128/Yrxn-hpinrya6814381.jpg";

  ProfilePage() {
    commonTitle = "我的资料";
  }

  @override
  Widget buildChild(BuildContext context) {
    return Container(
      color: Colors.white,
      child: Column(
        children: <Widget>[
          Container(
            margin: EdgeInsets.symmetric(vertical: 20),
            child: Stack(
              fit: StackFit.loose,
              alignment: Alignment.center,
              children: <Widget>[
                Image(
                  width: 95,
                  height: 95,
                  fit: BoxFit.cover,
                  image: AssetImage("assets/images/def_header_bg.png"),
                ),
                Image(
                  width: 95,
                  height: 95,
                  fit: BoxFit.cover,
                  image: AssetImage(
                      "assets/images/service_icon_default_avatar.png"),
                ),
                Positioned(
                  right: 0,
                  bottom: 0,
                  child: Image(
                    fit: BoxFit.cover,
                    image: AssetImage("assets/images/ic_take_photo.png"),
                  ),
                ),
              ],
            ),
          ),
          setItem("昵称", "手机用户2095", titleColor, decColor),
          CommonWidget.getCommonDivider(10),
          setItem("绑定手机", "187****2095", titleColor, decColor),
          CommonWidget.getCommonDivider(10),
          setItem("密码", "******", titleColor, decColor),
          CommonWidget.getCommonDivider(10),
          setItem("收货地址", "手机用户2095", titleColor, decColor),
        ],
      ),
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
                  fontSize: 14,
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
              fontSize: 14,
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
