import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_login/login/pages/page_login/login_bloc.dart';
import 'package:flutter_login/login/pages/page_login/login_state.dart';
import 'package:flutter_login/login/route/routes.dart';
import 'package:flutter_middle/application.dart';

class SplashPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print("SplashPage build");

    return BlocConsumer<AuthenticationBloc, AuthenticationState>(
      listener: (BuildContext context, AuthenticationState state) {
        if (state is AuthenticationUnauthenticated) {
          //没有授权，跳转到登录页
          Application.router.navigateTo(context, Routes.loginPage,
              clearStack: true, transition: TransitionType.fadeIn);
        }
        if (state is AuthenticationAuthenticated) {
          //已经授权，跳首页
          Application.router.navigateTo(context, Routes.homePage,
              clearStack: true, transition: TransitionType.fadeIn);
        }
      },
      builder: (BuildContext context, AuthenticationState state) {
        return Scaffold(
          resizeToAvoidBottomInset: false,
          body: GestureDetector(
            onTap: () {
              Application.router.navigateTo(context, Routes.loginPage);
            },
            child: Image(
              fit: BoxFit.fitWidth,
              alignment: Alignment.topCenter, //可以控制Image中图片的位置
              image: AssetImage("assets/images/login_bg_loading.png"),
            ),
          ),
        );
      },
    );
  }
}
