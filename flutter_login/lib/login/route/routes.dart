import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_login/login/pages/page_home/home.dart';
import 'package:flutter_login/login/pages/page_login/login.dart';
import 'package:flutter_login/login/pages/page_splash/splash.dart';

class Routes {
  static String root = "/";
  static String loginPage = "/login/loginPage";
  static String splashPage = "/login/SplashPage";
  static String homePage = "/login/HomePage";

  static void configureRoutes(Router router) {
    router.notFoundHandler = Handler(
        handlerFunc: (BuildContext context, Map<String, List<String>> params) {
      print("ROUTE WAS NOT FOUND !!!");
    });
    router.define(loginPage, handler: Handler(
        handlerFunc: (BuildContext context, Map<String, List<String>> params) {
          return LoginPage();
        }));
    router.define(splashPage, handler: Handler(
        handlerFunc: (BuildContext context, Map<String, List<String>> params) {
          return SplashPage();
        }));
    router.define(homePage, handler: Handler(
        handlerFunc: (BuildContext context, Map<String, List<String>> params) {
          return HomePage();
        }));
//    router.define(demoSimple, handler: demoRouteHandler);
//    router.define(demoSimpleFixedTrans,
//        handler: demoRouteHandler, transitionType: TransitionType.inFromLeft);
//    router.define(demoFunc, handler: demoFunctionHandler);
//    router.define(deepLink, handler: deepLinkHandler);
  }
}
