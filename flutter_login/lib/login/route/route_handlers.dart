import 'package:fluro/fluro.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_login/login/pages/page_home/home.dart';
import 'package:flutter_login/login/pages/page_login/login.dart';
import 'package:flutter_login/login/pages/page_splash/splash.dart';

var loginPageHandler = Handler(
    handlerFunc: (BuildContext context, Map<String, List<String>> params) {
  return LoginPage();
});

var splashPageHandler = Handler(
    handlerFunc: (BuildContext context, Map<String, List<String>> params) {
      return SplashPage();
    });

var homePageHandler = Handler(
    handlerFunc: (BuildContext context, Map<String, List<String>> params) {
      return HomePage();
    });
