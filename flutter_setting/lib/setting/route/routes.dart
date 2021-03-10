import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:flutter_setting/setting/route/route_handlers.dart';

class Routes {
  static String root = "/";
  static String profilePage = "/setting/profilePage";

  static void configureRoutes(FluroRouter router) {
    router.notFoundHandler = Handler(
        handlerFunc: (BuildContext context, Map<String, List<String>> params) {
      print("ROUTE WAS NOT FOUND !!!");
    });
    router.define(profilePage, handler: profilePageHandler);
//    router.define(demoSimple, handler: demoRouteHandler);
//    router.define(demoSimpleFixedTrans,
//        handler: demoRouteHandler, transitionType: TransitionType.inFromLeft);
//    router.define(demoFunc, handler: demoFunctionHandler);
//    router.define(deepLink, handler: deepLinkHandler);
  }
}
