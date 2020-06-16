import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class Store {
  //  我们将会在main.dart中runAPP实例化init
  static init<T extends ChangeNotifier>(T model, {@required Widget child}) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider<T>(create: (_) => model),
      ],
      child: child,
    );
  }

  //会rebuild
  static T watch<T>(context) {
//    return context.watch<T>();
    return Provider.of(context, listen: true);
  }

  //仅仅读取value
  static T read<T>(context) {
//    return context.read<T>();
    return Provider.of(context, listen: false);
  }

  //  通过Consumer获取状态数据
  static Consumer connect<T>({builder, child}) {
    return Consumer<T>(builder: builder, child: child);
  }

  static Selector selector<A, S>({builder, selector, shouldRebuild, child}) {
    return Selector<A, S>(
        builder: builder,
        selector: selector,
        shouldRebuild: shouldRebuild,
        child: child);
  }
}
