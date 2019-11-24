import 'package:fluro/fluro.dart';
import 'package:annotation_route/route.dart';
import 'package:flutter/widgets.dart';

class MyPageOption {
  String url;
  Map<String, dynamic> query;
  MyPageOption(this.url, this.query);
}

class Router {
  static String root = "/";
  static String home = "/home";
  static String collectionPage = '/collection-page';
  static String collectionFullPage = '/collection-full-page';
}
