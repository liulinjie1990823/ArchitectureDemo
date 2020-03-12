import 'dart:ui';

import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_login/login/repository/user_repository.dart';
import 'package:flutter_login/login/route/routes.dart';
import 'package:flutter_middle/application.dart';

import 'login/pages/page_login/login.dart';

void main() => runApp(_widgetForRoute(window.defaultRouteName));

class MyApp extends StatelessWidget {
  final userRepository = UserRepository();

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
    case 'loginPage':
      return Login();
    default:
      return Login();
  }
}
