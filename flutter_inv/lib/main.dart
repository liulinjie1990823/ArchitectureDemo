import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_inv/inv/router/router.dart';
import 'package:flutter_middle/api/api_manager.dart';

import 'simple_page_widgets.dart';

void main() {
  runApp(InvApp());
}

class InvApp extends StatefulWidget {
  static String FLUTTER_INV = "flutter_inv";

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<InvApp> {
  @override
  void initState() {
    super.initState();

    Map<String, PageBuilder> builders = {
      'embeded': (String pageName, Map<String, dynamic> params, String _) =>
          EmbeddedFirstRouteWidget(),
      'first': (String pageName, params, String _) => FirstRouteWidget(),
      'firstFirst': (String pageName, Map<String, dynamic> params, String _) =>
          FirstFirstRouteWidget(),
      'second': (String pageName, params, String _) => SecondRouteWidget(),
      'secondStateful':
          (String pageName, Map<String, dynamic> params, String _) =>
              SecondStatefulRouteWidget(),
      'tab': (String pageName, params, String _) => TabRouteWidget(),
      'platformView':
          (String pageName, Map<String, dynamic> params, String _) =>
              PlatformRouteWidget(),
      'flutterFragment':
          (String pageName, Map<String, dynamic> params, String _) =>
              FragmentRouteWidget(params),

      ///可以在native层通过 getContainerParams 来传递参数
      'flutterPage': (String pageName, Map<String, dynamic> params, String _) {
        print('flutterPage params:$params');

        return FlutterRouteWidget(params: params);
      },
    };
    builders.addAll(InvRouter.router);

    FlutterBoost.singleton.registerPageBuilders(builders);
    FlutterBoost.singleton
        .addBoostNavigatorObserver(TestBoostNavigatorObserver());

    ApiManager.getProxy();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Boost example',
        builder: FlutterBoost.init(postPush: _onRoutePushed),
        home: Container(color: Colors.white));
  }

  void _onRoutePushed(
    String pageName,
    String uniqueId,
    Map<String, dynamic> params,
    Route<dynamic> route,
    Future<dynamic> _,
  ) {}
}

class TestBoostNavigatorObserver extends NavigatorObserver {
  @override
  void didPush(Route<dynamic> route, Route<dynamic> previousRoute) {
    print('flutterboost#didPush');
  }

  @override
  void didPop(Route<dynamic> route, Route<dynamic> previousRoute) {
    print('flutterboost#didPop');
  }

  @override
  void didRemove(Route<dynamic> route, Route<dynamic> previousRoute) {
    print('flutterboost#didRemove');
  }

  @override
  void didReplace({Route<dynamic> newRoute, Route<dynamic> oldRoute}) {
    print('flutterboost#didReplace');
  }
}
