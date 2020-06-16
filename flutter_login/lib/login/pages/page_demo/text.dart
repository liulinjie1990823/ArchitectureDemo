import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/widgets/common_widget.dart';

class TextPage extends CommonTitleBarPage {
  @override
  String centerWidgetText(BuildContext context) {
    return "TextPage";
  }

  @override
  Color mainColor() {
    return CommonColor.COMMON_MAIN_COLOR;
  }

  @override
  Widget buildChild(BuildContext context) {
    return Container(
      width: double.infinity,
      color: CommonColor.COMMON_WHITE,
      child: Text(
        "datadata哒哒哒哒哒哒反倒是飞机婚博会的事发后撒复合肥就不能发肌钙蛋白十个八个爬到和",
        style: TextStyle(fontSize: 20),
      ),
    );
  }
}
