import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_boost/container/boost_page_route.dart';
import 'package:flutter_main/main/pages/page_home/home.dart';
import 'package:flutter_main/main/pages/page_tab/tab.dart';

class MainRouter {
  static Map<String, PageBuilder> router = {
    'mainPage': (String pageName, Map<String, dynamic> params, String _) {
      print('mainPage params:$params');
      return TabPage();
    }
  };

  static String root = "/";
  static String loginPage = "/login/loginPage";
  static String splashPage = "/login/SplashPage";
  static String homePage = "/login/HomePage";

  static void configureRoutes(Router router) {
    router.notFoundHandler = Handler(
        handlerFunc: (BuildContext context, Map<String, List<String>> params) {
      print("ROUTE WAS NOT FOUND !!!");
    });
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
