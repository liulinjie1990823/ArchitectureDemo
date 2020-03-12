import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class StatusBarUtil {
  static void statusBarTransparent(bool textWhite) {
    SystemUiOverlayStyle systemUiOverlayStyle = SystemUiOverlayStyle(
      systemNavigationBarColor: Color(0xFF000000),
      //android O以上
      systemNavigationBarDividerColor: null,
      //android P以上
      systemNavigationBarIconBrightness: Brightness.light,
      //android O以上，控制背景色
      statusBarColor: Colors.transparent,
      //android M以上，控制文本和icon颜色
      statusBarIconBrightness: textWhite ? Brightness.light : Brightness.dark,
      //android M以上
      statusBarBrightness: textWhite ? Brightness.dark : Brightness
          .light, //ios中使用
    );
    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);
  }
}
