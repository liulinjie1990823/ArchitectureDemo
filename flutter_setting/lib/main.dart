import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter_setting/setting/pages/page_setting/setting.dart';

void main() => runApp(_widgetForRoute(window.defaultRouteName));

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: _widgetForRoute(window.defaultRouteName),
    );
  }
}

//页面跳转
Widget _widgetForRoute(String route) {
  switch (route) {
    case 'setting':
      return Setting();
    default:
      return Setting();
  }
}
