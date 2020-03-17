import 'dart:ui';

import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_base/utils/status_bar_util.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_login/login/pages/page_login/login_bloc.dart';
import 'package:flutter_login/login/pages/page_login/login_event.dart';
import 'package:flutter_login/login/pages/page_login/login_state.dart';
import 'package:flutter_login/login/pages/page_splash/splash.dart';
import 'package:flutter_login/login/repository/user_repository.dart';
import 'package:flutter_login/login/route/routes.dart';
import 'package:flutter_middle/application.dart';
import 'package:flutter_middle/configs/common_color.dart';

void main() => runApp(_widgetForRoute(window.defaultRouteName));

/// 登录测试页面
class LoginTest extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocProvider<AuthenticationBloc>(
      create: (context) =>
      AuthenticationBloc()
        ..add(AppStartEvent()),
      child: App(),
    );
  }
}

class App extends StatelessWidget {
  final UserRepository _userRepository = UserRepository();
  final ThemeData _themeData = ThemeData(
    //TextFiled边框
    primaryColor: Color(CommonColor.C_MAIN_COLOR),
    //TextFiled边框
    primarySwatch: Colors.blue,
    //
    accentColor: Colors.green,

    //光标颜色
    cursorColor: Color(CommonColor.C_MAIN_COLOR),
    //
    textTheme:
    TextTheme(subhead: TextStyle(textBaseline: TextBaseline.alphabetic)),
    //输入设置
    inputDecorationTheme: InputDecorationTheme(
//          fillColor: Colors.cyan,
//          filled: true,
      hintStyle: TextStyle(
        fontSize: 16,
        textBaseline: TextBaseline.alphabetic,
        color: Color(CommonColor.C_HINT_TEXT),
      ),
      labelStyle: TextStyle(
        fontSize: 16,
        textBaseline: TextBaseline.alphabetic,
        color: Color(CommonColor.C_HINT_TEXT),
      ),
      enabledBorder: OutlineInputBorder(
        borderSide: BorderSide(
          style: BorderStyle.none,
          color: Color(CommonColor.C_EEEEEE),
          width: 1,
        ),
      ),
      focusedBorder: OutlineInputBorder(
        borderSide: BorderSide(
          style: BorderStyle.none,
          color: Color(CommonColor.C_MAIN_COLOR),
          width: 1,
        ),
      ),
    ),
  );

  @override
  Widget build(BuildContext context) {
    print("app build");
    StatusBarUtil.statusBarTransparent(false);

    return BlocProvider<AuthenticationBloc>(
      create: (context) =>
      AuthenticationBloc(userRepository: _userRepository)
        ..add(AppStartEvent()),
      child: MaterialApp(
        title: 'Login Demo',
        theme: _themeData,
        home: BlocConsumer<AuthenticationBloc, AuthenticationState>(
          listener: (BuildContext context, AuthenticationState state) {
            if (state is AuthenticationLoading) {
              //正在授权
            }
          },
          builder: (BuildContext context, AuthenticationState state) {
            if (state is AuthenticationUninitialized) {
              //未开始认证，跳闪屏页面
              return SplashPage();
            }
            return SplashPage();
          },
        ),
      ),
    );
  }
}

//页面跳转
Widget _widgetForRoute(String route) {
  final router = Router();
  Routes.configureRoutes(router);
  Application.router = router;

  switch (route) {
    default:
      return App();
  }
}
