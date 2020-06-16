import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter_login/login/pages/page_demo/button.dart';
import 'package:flutter_login/login/pages/page_demo/image.dart';
import 'package:flutter_login/login/pages/page_demo/layout.dart';
import 'package:flutter_login/login/pages/page_demo/text.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/widgets/common_widget.dart';

void main() {
  runApp(MaterialApp(
    home: Demo(),
  ));
}

class JumpVo {
  String _string;
  Widget _widget;

  JumpVo(this._string, this._widget);
}

class Demo extends CommonPage {
  List<JumpVo> demos = [];

  @override
  Color pageColor() {
    return CommonColor.COMMON_MAIN_COLOR;
  }

  @override
  bool useSafeArea(BuildContext context) {
    return true;
  }

  Demo() {
    demos.add(JumpVo("text", TextPage()));
    demos.add(JumpVo("layout", LayoutPage()));
    demos.add(JumpVo("button", ButtonPage()));
    demos.add(JumpVo("image", ImagePage()));
  }

  @override
  Widget buildChild(BuildContext context) {
    return ListView.separated(
      itemBuilder: (context, index) {
        return Container(
          padding: EdgeInsets.all(10),
          child: GestureDetector(
            onTap: () {
              Navigator.push(context, MaterialPageRoute(builder: (_) {
                return demos[index]._widget;
              }));
            },
            child: Text(
              demos[index]._string,
              style: TextStyle(fontSize: 20),
            ),
          ),
        );
      },
      separatorBuilder: (BuildContext context, int index) {
        return Divider(
          color: CommonColor.COMMON_WHITE,
        );
      },
      itemCount: demos.length,
    );
  }
}
