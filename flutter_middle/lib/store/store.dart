import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
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
    return Provider.of<T>(context, listen: true);
  }

  //仅仅读取value
  static T read<T>(context) {
//    return context.read<T>();
    return Provider.of<T>(context, listen: false);
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

class BlocStore {
//  B extends Bloc<dynamic, S>, S
  static init<T extends Bloc<dynamic, dynamic>>(T provider,
      {@required Widget child}) {
    return BlocProvider<T>(
      create: (BuildContext context) => provider,
      child: child,
    );
  }

  static BlocConsumer consumer<B extends Bloc<dynamic, S>, S>({
    @required builder,
    @required listener,
    bloc,
    buildWhen,
    listenWhen,
  }) {
    return BlocConsumer<B, S>(
        listener: listener,
        builder: builder,
        cubit: bloc,
        buildWhen: buildWhen,
        listenWhen: listenWhen);
  }

  static T read<T extends Bloc<dynamic, dynamic>>(context) {
    return BlocProvider.of<T>(context);
  }
}
