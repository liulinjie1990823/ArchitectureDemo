import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class StatusBarUtil {
  static void statusBarTransparent(bool textWhite) {
    SystemUiOverlayStyle systemUiOverlayStyle = SystemUiOverlayStyle(
      systemNavigationBarColor: Color(0xFF000000),
      systemNavigationBarDividerColor: null,
      statusBarColor: null,
      systemNavigationBarIconBrightness: Brightness.light,
      statusBarIconBrightness: textWhite ? Brightness.light : Brightness.dark,
      statusBarBrightness: textWhite ? Brightness.dark : Brightness.light,
    );
    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);
  }
}
