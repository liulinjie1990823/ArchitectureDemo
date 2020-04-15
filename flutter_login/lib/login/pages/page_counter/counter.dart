import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "provider",
      home: HomePage(),
    );
  }
}

class LoginModel extends ChangeNotifier {
  ValueNotifier<int> count = ValueNotifier(0);
}

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var model = LoginModel();
    ValueNotifier<int> count = model.count;
    //在HomePage里面写状态，它的作用域只在HomePage中
    return MultiProvider(
      providers: [
        ChangeNotifierProvider<LoginModel>(create: (_) => model),
        ValueListenableProvider.value(
          value: count,
//            child: Row(
//              mainAxisAlignment: MainAxisAlignment.center,
//              children: <Widget>[LeftView(), CenterView(), RightView()],
//            ),
        )
      ],
      child: Scaffold(
        appBar: AppBar(title: Text("home")),
        body: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[LeftView(), CenterView(), RightView()],
        ),
        floatingActionButton: Builder(builder: (context) {
          print(
              "floatingActionButton:build${Provider
                  .of<LoginModel>(context, listen: true)
                  .count
                  .value}");
          return FloatingActionButton(
//            onPressed: () => count.value += 1,
            onPressed: () =>
            Provider
                .of<LoginModel>(context, listen: true)
                .count
                .value += 1,
            child: Icon(Icons.add),
          );
        }),
      ),
    );
  }
}

class LeftView extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Consumer<int>(
        child: MyText(),
        builder: (context, value, child) {
          print("LeftView:build");
          return Container(
              width: 100,
              height: 100,
              color: Colors.blue,
              alignment: Alignment.center,
              child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    child,
                    Text("${Provider.of<int>(context, listen: false)})")
                  ]));
        });
  }
}

class MyText extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print("MyText:build");
    return Text("数量");
  }
}

class CenterView extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print("CenterView:build");
    return Container(
      width: 100,
      height: 100,
      color: Colors.pink,
      alignment: Alignment.center,
      child: Text(
        //listen: false 不监听状态改变
        "数量\n",
        textAlign: TextAlign.center,
      ),
    );
  }
}

class RightView extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print("RightView:build");
    return Container(
      width: 100,
      height: 100,
      color: Colors.green,
      alignment: Alignment.center,
      child: Text(
        "数量\n${Provider.of<int>(context)}",
        textAlign: TextAlign.center,
      ),
    );
  }
}
