import 'package:flutter/material.dart';
import 'package:flutter_middle/widgets/common_widget.dart';
import 'dart:math';

class DialogPage extends CommonTitleBarPage {
  @override
  Widget buildChild(BuildContext context) {
    return ListView(
      children: <Widget>[
        FlatButton(
          onPressed: () {
            showTransBackgroundDialog(context);
          },
          child: Container(
            width: double.infinity,
            height: 50,
            child: Text("透明背景"),
          ),
        ),
        FlatButton(
          onPressed: () {
            showOtherBackgroundDialog(context,Colors.red);
          },
          child: Container(
            width: double.infinity,
            height: 50,
            child: Text("带颜色背景,而且时间不太一样"),
          ),
        ),
        FlatButton(
          onPressed: () {
            showDialogWithOffset(context,handle: fromLeft);
          },
          child: Container(
            width: double.infinity,
            height: 50,
            child: Text("从左进入"),
          ),
        ),
        FlatButton(
          onPressed: () {
            showDialogWithOffset(context,handle: fromRight);
          },
          child: Container(
            width: double.infinity,
            height: 50,
            child: Text("从右进入"),
          ),
        ),
        FlatButton(
          onPressed: () {
            showDialogWithOffset(context,handle: fromTop);
          },
          child: Container(
            width: double.infinity,
            height: 50,
            child: Text("从上进入"),
          ),
        ),
        FlatButton(
          onPressed: () {
            showDialogWithOffset(context,handle: fromBottom);
          },
          child: Container(
            width: double.infinity,
            height: 50,
            child: Text("从下进入"),
          ),
        ),

        FlatButton(
          onPressed: () {
            showDialogWithScaleTransition(context);
          },
          child: Container(
            width: double.infinity,
            height: 50,
            child: Text("缩放"),
          ),
        ),

        FlatButton(
          onPressed: () {
            showDialogWithScaleTransitionAndTansform(context);
          },
          child: Container(
            width: double.infinity,
            height: 50,
            child: Text("飘过来"),
          ),
        ),

//        buildButton(context, "dialog1", showTransBackgroundDialog),
//        buildButton(context, "透明背景", showTransBackgroundDialog(context)),
//        buildButton(
//          context,
//          "带颜色背景,而且时间不太一样",
//          () =>
//              showOtherBackgroundDialog(context, Colors.black.withOpacity(0.8)),
//        ),
//        buildButton(context, "使用transitionBuilder",
//            showDialogWithToTestTransitionDialog(context)),
//        buildButton(context, "从左进入",
//            () => showDialogWithOffset(context, handle: fromLeft)),
//        buildButton(context, "从右进入",
//            () => showDialogWithOffset(context, handle: fromRight)),
//        buildButton(context, "从上进入",
//            () => showDialogWithOffset(context, handle: fromTop)),
//        buildButton(context, "从下进入",
//            () => showDialogWithOffset(context, handle: fromBottom)),
//        buildButton(context, "从左上进入",
//            () => showDialogWithOffset(context, handle: fromTopLeft)),
//        buildButton(context, "缩放", showDialogWithScaleTransition(context)),
//        buildButton(
//            context, "飘过来", showDialogWithScaleTransitionAndTansform(context)),
      ],
    );
  }

  @override
  String centerWidgetText(BuildContext context) {
    return "dialog";
  }

  showTransBackgroundDialog(BuildContext context) {
    showGeneralDialog(
      context: context,
      barrierLabel: "你好",
      barrierDismissible: true,
      transitionDuration: Duration(milliseconds: 300),
      pageBuilder: (BuildContext context, Animation animation,
          Animation secondaryAnimation) {
        return Center(
          child: Material(
            child: Container(
              color: Colors.black.withOpacity(animation.value),
              child: Text("我是一个可变的"),
            ),
          ),
        );
      },
    );
  }

  showOtherBackgroundDialog(context, Color color) {
    showGeneralDialog(
      context: context,
      barrierLabel: "你好",
      barrierDismissible: true,
      transitionDuration: Duration(milliseconds: 300),
      barrierColor: color,
      pageBuilder: (BuildContext context, Animation animation,
          Animation secondaryAnimation) {
        return Center(
          child: Material(
            child: Container(
              color: Colors.black.withOpacity(animation.value),
              child: Text("我是一个可变的"),
            ),
          ),
        );
      },
    );
  }

  showDialogWithToTestTransitionDialog(context) {
    showGeneralDialog(
      context: context,
      barrierLabel: "你好",
      barrierDismissible: true,
      transitionDuration: Duration(milliseconds: 1000),
      barrierColor: Colors.white.withOpacity(0.5),
      pageBuilder: (
        BuildContext context,
        Animation animation,
        Animation secondaryAnimation,
      ) {
        return Container(
          child: Text("我是一个dialog"),
        );
      },
      transitionBuilder: (
        BuildContext context,
        Animation<double> animation,
        Animation<double> secondaryAnimation,
        Widget child,
      ) {
        return Center(
          child: Material(
            child: Container(
              color: Colors.red.withOpacity(animation.value),
              child: child,
            ),
          ),
        );
      },
    );
  }

  showDialogWithOffset(context, {OffsetHandle handle = fromLeft}) {
    showGeneralDialog(
      context: context,
      barrierColor: Colors.black.withOpacity(0.5),
      barrierLabel: "",
      barrierDismissible: true,
      transitionDuration: const Duration(milliseconds: 300),
      pageBuilder: (
        BuildContext context,
        Animation animation,
        Animation secondaryAnimation,
      ) {
        return Center(
          child: Material(
            child: Container(
              child: Text("我是dialog"),
            ),
          ),
        );
      },
      transitionBuilder: (ctx, animation, _, child) {
        return FractionalTranslation(
          translation: handle(animation),
          child: child,
        );
      },
    );
  }

  showDialogWithScaleTransition(context) {
    showGeneralDialog(
      context: context,
      barrierLabel: "",
      barrierColor: Colors.black.withOpacity(0.5),
      transitionDuration: const Duration(milliseconds: 300),
      barrierDismissible: true,
      pageBuilder: (BuildContext context, Animation animation,
          Animation secondaryAnimation) {
        return Center(
          child: Image.asset("assets/images/image_4.jpg"),
        );
      },
      transitionBuilder: (_, anim, __, child) {
        return ScaleTransition(
          alignment: Alignment.center, // 添加这个
          scale: anim,
          child: child,
        );
      },
    );
  }

  showDialogWithScaleTransitionAndTansform(context) {
    showGeneralDialog(
      context: context,
      barrierLabel: "",
      barrierColor: Colors.black.withOpacity(0.5),
      transitionDuration: const Duration(milliseconds: 2000),
      barrierDismissible: true,
      pageBuilder: (BuildContext context, Animation animation,
          Animation secondaryAnimation) {
        return Center(
          child: Image.asset("assets/images/image_4.jpg"),
        );
      },
      transitionBuilder: (_, anim, __, child) {
        var radians = 2 * pi * anim.value;
        var matrix = Matrix4.rotationY(radians);
        return Transform(
          child: ScaleTransition(
            scale: anim,
            child: child,
          ),
          transform: matrix,
        );
      },
    );
  }

  Future dialog1(context) {
    return showGeneralDialog(
      context: context,
      barrierLabel: "你好",
      barrierDismissible: true,
      transitionDuration: Duration(milliseconds: 500),
      barrierColor: Colors.black.withOpacity(0.5),
      pageBuilder: (BuildContext context, Animation animation,
          Animation secondaryAnimation) {
        return Center(
          child: Material(
            child: Container(
              color: Colors.black.withOpacity(animation.value),
              child: Text("我是一个可变的"),
            ),
          ),
        );
      },
    );
  }
}

typedef OffsetHandle = Offset Function(Animation animation);

Offset fromLeft(Animation animation) {
  return Offset(animation.value - 1, 0);
}

Offset fromRight(Animation animation) {
  return Offset(1 - animation.value, 0);
}

Offset fromTop(Animation animation) {
  return Offset(0, animation.value - 1);
}

Offset fromBottom(Animation animation) {
  return Offset(0, 1 - animation.value);
}

Offset fromTopLeft(Animation anim) {
  return fromLeft(anim) + fromTop(anim);
}
