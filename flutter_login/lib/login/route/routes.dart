import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_login/login/route/route_handlers.dart';

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
    router.define(loginPage, handler: loginPageHandler);
    router.define(splashPage, handler: splashPageHandler);
    router.define(homePage, handler: homePageHandler);
//    router.define(demoSimple, handler: demoRouteHandler);
//    router.define(demoSimpleFixedTrans,
//        handler: demoRouteHandler, transitionType: TransitionType.inFromLeft);
//    router.define(demoFunc, handler: demoFunctionHandler);
//    router.define(deepLink, handler: deepLinkHandler);
  }
}
