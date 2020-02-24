import 'package:flutter/material.dart';
import 'package:flutter_base/utils/status_bar_util.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.

  final List<String> entries = <String>['A', 'B', 'C'];
  final List<int> colorCodes = <int>[600, 500, 100];

  @override
  Widget build(BuildContext context) {
    StatusBarUtil.statusBarTransparent(true);
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Container(
        color: Colors.blue,
        child: SafeArea(
          child: ListView.separated(
            padding: const EdgeInsets.all(8),
            itemCount: entries.length,
            itemBuilder: (BuildContext context, int index) {
              return Container(
                height: 50,
                color: Colors.amber[colorCodes[index]],
                child: Align(
                    alignment: Alignment.centerLeft,
                    child: Text(
                      'Entry ${entries[index]}',
                      style: TextStyle(
                        decoration: TextDecoration.none,
                        fontSize: 13,
                      ),
                    )),
              );
            },
            separatorBuilder: (BuildContext context, int index) =>
            const Divider(),
          ),
        ),
      ),
    );
  }
}
