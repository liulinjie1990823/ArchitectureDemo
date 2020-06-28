import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_login/login/pages/page_login/login_bloc.dart';
import 'package:flutter_login/login/pages/page_login/login_state.dart';
import 'package:flutter_login/login/router/router.dart';
import 'package:flutter_main/main/pages/page_tab/tab.dart';
import 'package:flutter_middle/application.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/widgets/common_widget.dart';

class SplashPage extends CommonPage {
  @override
  Widget buildChild(BuildContext context) {

    return BlocConsumer<AuthenticationBloc, AuthenticationState>(
      listener: (BuildContext context, AuthenticationState state) {
        if (state is AuthenticationUnauthenticated) {
          //没有授权，跳转到登录页
          Application.router.navigateTo(context, LoginRouter.loginPage,
              clearStack: true, transition: TransitionType.fadeIn);
        }

        if (state is AuthenticationAuthenticated) {
          //已经授权，跳首页
          Navigator.push(context, MaterialPageRoute(builder: (_) {
            return TabPage();
          }));
        }
      },
      builder: (BuildContext context, AuthenticationState state) {
        return Scaffold(
          resizeToAvoidBottomInset: false,
          body: GestureDetector(
            onTap: () {
            },
            child: Container(
              width: double.infinity,
              height: double.infinity,
              color: CommonColor.COMMON_00c160,
              child: Image(
                fit: BoxFit.cover,
                image: AssetImage("assets/images/login_bg_loading.png"),
              ),
            ),
          ),
        );
      },
    );
  }
}
