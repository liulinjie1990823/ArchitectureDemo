import 'package:flutter/material.dart';
import 'package:flutter_login/login/pages/page_login/counter_model.dart';
import 'package:provider/provider.dart';

class Store {
  //  我们将会在main.dart中runAPP实例化init
  static init({@required Widget child}) {
    var model = LoginModel();
    return MultiProvider(
      providers: [
        ChangeNotifierProvider<LoginModel>(create: (_) => model),
        ValueListenableProvider<int>.value(value: model.index),
        ValueListenableProvider<bool>.value(value: model.canGetCode),
//        ValueListenableProvider<bool>.value(value: model.canLogin),
//        ValueListenableProvider<bool>.value(value: model.canLogin),
      ],
      child: child,
    );
  }

  //  通过Provider.value<T>(context)获取状态数据
  static T value<T>(context, {bool listen = false}) {
    return Provider.of(context, listen: listen);
  }

  //  通过Consumer获取状态数据
  static Consumer connect<T>({builder, child}) {
    return Consumer<T>(builder: builder, child: child);
  }
}
