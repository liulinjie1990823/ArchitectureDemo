import 'package:flutter/material.dart';
import 'dart:ui'; // window

void main() => runApp(_widgetForRoute(window.defaultRouteName));

// 路由方法
Widget _widgetForRoute(String route) {
  switch (route) {
    // 此处很重要，iOS/Android项目中均设置了路由到flutter页面的路由参数
    case 'Page':
      return null;
    default:
      return Center(
        child: Text(
          "Unknown route: $route",
          textDirection: TextDirection.ltr,
        ),
      );
  }
}
