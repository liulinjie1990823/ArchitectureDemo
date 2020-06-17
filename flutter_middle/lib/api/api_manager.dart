import 'dart:io';

import 'package:dio/adapter.dart';
import 'package:dio/dio.dart';

import 'package:flutter/foundation.dart';

class ApiManager {
  static final Map<String, MiddleRestClient> sCache =
      <String, MiddleRestClient>{}; // 缓存保存对象

  static Dio _sDio;

  static Dio dio() {
    if (_sDio == null) {
      _sDio = new Dio();
      _sDio.options.connectTimeout = 20000;
      _sDio.options.receiveTimeout = 10000;

      (_sDio.httpClientAdapter as DefaultHttpClientAdapter).onHttpClientCreate =
          (client) {
        //release去掉代理
        if (!kReleaseMode) {
          client.findProxy = (uri) {
            return "PROXY 192.168.88.173:8888";
          };
        }
        client.badCertificateCallback =
            (X509Certificate cert, String host, int port) {
          return true;
        };
      };
    }

    _sDio.options.headers["device-name"] = "demo header";
    _sDio.options.headers["device-id"] = "demo header";
    _sDio.options.headers["os-name"] = "Android";
    _sDio.options.headers["os-version"] = "demo header";
    _sDio.options.headers["client-id"] = "demo header";
    _sDio.options.headers["app-key"] = "demo header";
    _sDio.options.headers["app-version"] = "demo header";
    _sDio.options.headers["app-channel"] = "demo header";

    _sDio.options.headers["User-Agent"] = "demo header";
    _sDio.options.headers["Authorization"] = "demo header";

    _sDio.options.headers["resolution"] = "demo header";

    _sDio.options.headers["site-city-name"] = "demo header";
    _sDio.options.headers["city-id"] = "demo header";
    _sDio.options.headers["visit-city-name"] = "demo header";

    _sDio.options.headers["page-id"] = "demo header";
    _sDio.options.headers["view-id"] = "demo header";

    return _sDio;
  }
}

class MiddleRestClient {
//  factory MiddleRestClient(Dio dio, {String baseUrl}) {
//    if (baseUrl == null || baseUrl.length == 0) {
//      return MiddleRestClient.internal();
//    }
//    if (ApiManager.sCache.containsKey(baseUrl)) {
//      return ApiManager.sCache[baseUrl];
//    } else {
//      final restClient = MiddleRestClient.internal2(dio,baseUrl:baseUrl);
//      ApiManager.sCache[baseUrl] = restClient;
//      return restClient;
//    }
//  }

  MiddleRestClient.internal2(Dio dio, {String baseUrl});

  MiddleRestClient.internal1(String baseUrl);

  MiddleRestClient.internal();

  MiddleRestClient();
}

class Logger {
  final String name;
  bool mute = false;

  // _cache is library-private, thanks to
  // the _ in front of its name.
  static final Map<String, Logger> _cache = <String, Logger>{};

  factory Logger(String name) {
    if (_cache.containsKey(name)) {
      return _cache[name];
    } else {
      final logger = Logger.internal(name);
      _cache[name] = logger;
      return logger;
    }
  }

  Logger.internal(this.name);

  Logger.dada() : this.internal("dadad");

  void log(String msg) {
    if (!mute) print(msg);
  }
}

class Chipmunk {
  String name;
  int fame;

  Chipmunk.named(this.name, [this.fame]);

  Chipmunk.famous1() : this.named('Chip', 1000);

  factory Chipmunk.famous2() {
    var result = new Chipmunk.named('Chip');
    result.fame = 1000;
    return result;
  }
}
