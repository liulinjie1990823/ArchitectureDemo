import 'dart:ui';

import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_base/utils/status_bar_util.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_login/login/pages/page_home/home.dart';
import 'package:flutter_login/login/pages/page_login/login_bloc.dart';
import 'package:flutter_login/login/pages/page_login/login_event.dart';
import 'package:flutter_login/login/pages/page_login/login_state.dart';
import 'package:flutter_login/login/pages/page_splash/splash.dart';
import 'package:flutter_login/login/repository/user_repository.dart';
import 'package:flutter_login/login/route/routes.dart';
import 'package:flutter_middle/application.dart';
import 'package:flutter_middle/configs/common_color.dart';

import 'login/pages/page_login/login.dart';

//void main() => runApp(_widgetForRoute(window.defaultRouteName));
void main() => runApp(LoginTest());

/// 登录测试页面
class LoginTest extends StatelessWidget {
  // 数据仓库
  final userRepository = UserRepository();

  @override
  Widget build(BuildContext context) {
    return BlocProvider<AuthenticationBloc>(
      create: (context) =>
      AuthenticationBloc(userRepository: userRepository)
        ..add(AppStartEvent()),
      child: App(userRepository: userRepository),
    );
  }
}

class App extends StatelessWidget {
  final UserRepository userRepository;

  App({Key key, @required this.userRepository}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    StatusBarUtil.statusBarTransparent(false);
    return MaterialApp(
      title: 'Login Demo',
      theme: ThemeData(
        //TextFiled边框
        primaryColor: Color(CommonColor.C_MAIN_COLOR),
        //TextFiled边框
        primarySwatch: Colors.blue,
        //
        accentColor: Colors.green,

        //光标颜色
        cursorColor: Color(CommonColor.C_MAIN_COLOR),
        //
        textTheme: TextTheme(
            subhead: TextStyle(textBaseline: TextBaseline.alphabetic)),
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
      ), //主题
      home: BlocBuilder<AuthenticationBloc, AuthenticationState>(
        builder: (BuildContext context, AuthenticationState state) {
          if (state is AuthenticationAuthenticated) {
            //已经授权，跳首页
            return HomePage();
          } else if (state is AuthenticationUnauthenticated) {
            //没有授权，跳登录页
            return LoginPage(userRepository: userRepository);
          } else if (state is AuthenticationLoading) {
            //正在授权
            return SplashPage();
          } else {
            return SplashPage();
          }
        },
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
    case 'loginPage':
      return LoginPage(userRepository: UserRepository());
    default:
      return LoginPage(userRepository: UserRepository());
  }
}
