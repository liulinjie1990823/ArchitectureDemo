import 'package:flutter/material.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/widgets/common_widget.dart';

class CouponPage extends CommonTitleBarPage {
  @override
  Widget buildChild(BuildContext context) {
    return ListView.separated(
      itemCount: 50,
//        itemExtent: 30.0,
      itemBuilder: (BuildContext context, int index) {
        print("这是第$index个条目");
        return Container(
          height: 50,
          child: Text("这是第$index个条目"),
        );
      },
      separatorBuilder: (BuildContext context, int index) {
        return Divider(
          color: CommonColor.COMMON_MAIN_COLOR,
        );
      },
    );
  }
}
