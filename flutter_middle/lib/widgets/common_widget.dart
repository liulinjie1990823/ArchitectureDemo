import 'dart:math' as math;

import 'package:flutter/material.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/utils/display_util.dart';
import 'package:flutter_base/utils/status_bar_util.dart';
import 'package:flutter_middle/utils/log_util.dart';

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
      height: 1 / DisplayUtil.pixelRatio,
      color: Color(0xffeeeeee),
    );
  }

  static Widget getCommonCircularProgress({Color color}) {
    return Container(
      width: double.infinity,
      height: double.infinity,
      alignment: Alignment.center,
      color: Color(0xffeeeeee),
      child: Container(
        width: 50,
        height: 50,
        child: CircularProgressIndicator(
          backgroundColor: Colors.transparent,
          valueColor: AlwaysStoppedAnimation(color ?? Colors.blue),
        ),
      ),
    );
  }
}

//定义回调方法
typedef StatusBuilder<T> = Widget Function(
    BuildContext context, AsyncSnapshot<T> snapshot);

//状态管理
class CommonStatusBuilder<T> extends StatelessWidget {
  final AsyncSnapshot<T> snapshot;
  final StatusBuilder<T> builder;
  final Color color;

  const CommonStatusBuilder(
      {this.color, @required this.snapshot, @required this.builder});

  @override
  Widget build(BuildContext context) {
    switch (snapshot.connectionState) {
      case ConnectionState.none:
        return Text('Press button to start.');
      case ConnectionState.active:
      case ConnectionState.waiting:
        return CommonWidget.getCommonCircularProgress(
            color: color ?? CommonColor.COMMON_MAIN_COLOR);
      case ConnectionState.done:
        if (snapshot.hasError) {
          return Text('Error: ${snapshot.error}');
        }
        return builder(context, snapshot);
    }
    return null;
  }
}

//
abstract class CommonPage extends StatelessWidget {
  Widget buildChild(BuildContext context);

  bool useSafeArea(BuildContext context) {
    return false;
  }

  bool statusBarTextWhite() {
    return true;
  }

  Color pageColor() {
    return Colors.transparent;
  }

  @override
  Widget build(BuildContext context) {
    LogUtil.cPrint(this.runtimeType.toString() + " build");
    StatusBarUtil.statusBarTransparent(statusBarTextWhite());
    return Scaffold(
      resizeToAvoidBottomInset: true,
      body: DecoratedBox(
        decoration: BoxDecoration(color: pageColor()),
        child: Builder(
          builder: (BuildContext context) {
            bool safe = useSafeArea(context);
            if (safe) {
              return SafeArea(
                child: buildChild(context),
              );
            }
            return buildChild(context);
          },
        ),
      ),
    );
  }
}

//
abstract class CommonTitleBarPage extends StatelessWidget {
  Widget buildChild(BuildContext context);

  //region 颜色控制
  Color mainColor() {
    return Colors.white;
  }

  bool statusBarTextWhite() {
    return false;
  }

  Color titleBarColor() {
    return Colors.transparent;
  }

  Color titleBarTextColor() {
    return Colors.black;
  }

  //endregion

  //region 左右控件点击
  void onLeftTap(BuildContext context) {}

  void onRightTap(BuildContext context) {}

  //endregion

  //region 设置标题栏控件
  Widget leftWidget(BuildContext context) {
    return null;
  }

  Widget centerWidget(BuildContext context) {
    return null;
  }

  Widget rightWidget(BuildContext context) {
    return null;
  }

  //endregion


  //region 标题栏文案设置
  String centerWidgetText(BuildContext context);

  String leftWidgetText(BuildContext context) {
    return "";
  }

  String rightWidgetText(BuildContext context) {
    return "";
  }
  //endregion

  double titleBarHeight() {
    return 44;
  }

  Widget _title({double height}) {
    return Container(
      constraints:
          BoxConstraints.expand(width: double.infinity, height: height),
      decoration: BoxDecoration(color: titleBarColor()),
      child: Stack(
        children: <Widget>[
          Positioned(
              bottom: 0,
              top: 0,
              left: 0,
              child: Builder(
                builder: (BuildContext context) {
                  return GestureDetector(
                    onTap: () {
                      onLeftTap(context);
                    },
                    child: Builder(
                      builder: (BuildContext context) {
                        Widget left = leftWidget(context);
                        if (left != null) {
                          return left;
                        }
                        return Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.symmetric(horizontal: 15),
                          child: Text(
                            leftWidgetText(context),
                            style: TextStyle(
                                fontSize: 15, color: titleBarTextColor()),
                          ),
                        );
                      },
                    ),
                  );
                },
              )),
          Builder(
            builder: (BuildContext context) {
              return Text(
                centerWidgetText(context),
                style: TextStyle(fontSize: 17, color: titleBarTextColor()),
              );
            },
          ),
          Positioned(
              bottom: 0,
              top: 0,
              right: 0,
              child: Builder(
                builder: (BuildContext context) {
                  return GestureDetector(
                    onTap: () {
                      onRightTap(context);
                    },
                    child: Builder(
                      builder: (BuildContext context) {
                        Widget right = rightWidget(context);
                        if (right != null) {
                          return right;
                        }
                        return Container(
                          alignment: Alignment.centerRight,
                          padding: EdgeInsets.symmetric(horizontal: 15),
                          child: Text(
                            rightWidgetText(context),
                            style: TextStyle(
                                fontSize: 15, color: titleBarTextColor()),
                          ),
                        );
                      },
                    ),
                  );
                },
              )),
        ],
        alignment: Alignment.center,
        fit: StackFit.loose,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    LogUtil.cPrint(this.runtimeType.toString() + " build");
    StatusBarUtil.statusBarTransparent(statusBarTextWhite());
    return Scaffold(
      body: DecoratedBox(
        decoration: BoxDecoration(color: mainColor()),
        child: SafeArea(
          child: Column(
            children: <Widget>[
              _title(height: titleBarHeight()),
              Expanded(child: buildChild(context)),
            ],
          ),
        ),
      ),
    );
  }
}
