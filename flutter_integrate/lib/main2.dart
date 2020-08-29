import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or press Run > Flutter Hot Reload in a Flutter IDE). Notice that the
        // counter didn't reset back to zero; the application is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: InheritedWidgetTestRoute(),
    );
  }
}

class ShareDataWidget extends InheritedWidget {
  ShareDataWidget({@required this.data, Widget child}) : super(child: child);

  final int data; //需要在子树中共享的数据，保存点击次数

  //定义一个便捷方法，方便子树中的widget获取共享数据
  static ShareDataWidget of(BuildContext context) {
    return context.inheritFromWidgetOfExactType(ShareDataWidget);
  }

  //该回调决定当data发生变化时，是否通知子树中依赖data的Widget
  @override
  bool updateShouldNotify(ShareDataWidget old) {
    //如果返回true，则子树中依赖(build函数中有调用)本widget
    //的子widget的`state.didChangeDependencies`会被调用
    return true;
  }
}

class _TestWidget extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    print("_TestWidget build");
    return Text(ShareDataWidget.of(context).data.toString());
  }

}


class InheritedWidgetTestRoute extends StatefulWidget {
  @override
  _InheritedWidgetTestRouteState createState() =>
      new _InheritedWidgetTestRouteState();
}

class _InheritedWidgetTestRouteState extends State<InheritedWidgetTestRoute> {
  int count = 0;

  @override
  Widget build(BuildContext context) {
    return StatefulBuilder(
      builder: (BuildContext context, StateSetter setState){
        return ShareDataWidget(
          //使用ShareDataWidget
          data: count,
          child: Builder(
            builder: (_) {
              print("Column build");
              return Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Builder(
                    builder: (_) {
                      return Padding(
                        padding: const EdgeInsets.only(bottom: 20.0),
                        child: _TestWidget(), //子widget中依赖ShareDataWidget
                      );
                    },
                  ),
                  Builder(
                    builder: (_) {
                      print("RaisedButton build");
                      return RaisedButton(
                        child: Text("Increment"),
                        //每点击一次，将count自增，然后重新build,ShareDataWidget的data将被更新
                        onPressed: () => setState(() => ++count),
                      );
                    },
                  ),
                ],
              );
            },
          ),
        );
      },
    );
  }
}


