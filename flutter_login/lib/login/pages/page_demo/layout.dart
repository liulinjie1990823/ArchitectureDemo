import 'package:flutter/material.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/widgets/common_widget.dart';

class LayoutPage extends CommonTitleBarPage {
  @override
  Widget buildChild(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: <Widget>[
          Row(
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Text("left"),
              Expanded(
                child: Image(
                  image: AssetImage("assets/images/image_1.jpg"),
                  width: 100,
                  height: 100,
                ),
              ),
              Container(
                  color: CommonColor.COMMON_MAIN_COLOR,
                  alignment: Alignment.bottomCenter,
                  child: Text("right")),
            ],
          ),
          IntrinsicHeight(
            child: Row(
              mainAxisSize: MainAxisSize.max,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Text("left"),
                Expanded(
                  child: Image(
                    image: AssetImage("assets/images/image_1.jpg"),
                    width: 100,
                    height: 100,
                  ),
                ),
                Column(
                  children: <Widget>[
                    Spacer(),
                    Text("right"),
                  ],
                ),
              ],
            ),
          ),
          IntrinsicHeight(
            child: Row(
              mainAxisSize: MainAxisSize.max,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Text("left"),
                Expanded(
                  child: Image(
                    image: AssetImage("assets/images/image_1.jpg"),
                    width: 100,
                    height: 100,
                  ),
                ),
                Align(
                  alignment: Alignment.bottomCenter,
                  child: Text("right"),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  @override
  String centerWidgetText(BuildContext context) {
    return "Layout";
  }
}
