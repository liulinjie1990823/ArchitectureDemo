import 'dart:ui';

import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_setting/setting/application.dart';
import 'package:flutter_setting/setting/pages/page_setting/setting.dart';
import 'package:flutter_setting/setting/route/router.dart';

void main() => runApp(_widgetForRoute(window.defaultRouteName));

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return _widgetForRoute(window.defaultRouteName);
  }
}

//页面跳转
Widget _widgetForRoute(String route) {
  final router = Router();
  Routes.configureRoutes(router);
  Application.router = router;

  switch (route) {
    case 'setting':
      return Setting();
    default:
      return Setting();
  }
}
