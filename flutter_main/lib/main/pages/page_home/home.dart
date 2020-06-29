import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

class HomePage extends StatelessWidget {
//  void main() {
//    print("main start at ${DateTime.now()}");
//    download(); //没有加await关键字表示当前线程不需要等待(不会阻塞)
//    print("main end at ${DateTime.now()}");
//  }
//
//  void download() async {
//    print("download start at ${DateTime.now()}");
//    await bar(); //当前线程休眠5秒
//    print("download end at ${DateTime.now()}");
//  }

  Future<String> bar() async {
    print("bar 1E ${DateTime.now()}");
    print("bar 2E ${DateTime.now()}");
    print("bar 3E ${DateTime.now()}");
    print("bar 4E ${DateTime.now()}");
    print("bar 5E ${DateTime.now()}");
    print("bar 6E ${DateTime.now()}");
    return Future.value("hello");
  }

  void download() async {
    print("download start at ${DateTime.now()}");
    await new Future.delayed(const Duration(seconds: 5)); //当前线程休眠5秒
    print("download end at ${DateTime.now()}");
  }

  void test() {
    print("main start at ${DateTime.now()}");
    download(); //没有加await关键字表示当前线程不需要等待(不会阻塞)
    print("main end at ${DateTime.now()}");
  }

  _getIPAddress() async {
    var url = 'https://httpbin.org/ip';
    var httpClient = new HttpClient();

    String result;
    try {
      var request = await httpClient.getUrl(Uri.parse(url));
      var response = await request.close();
      if (response.statusCode == HttpStatus.OK) {
        var json = await response.transform(utf8.decoder).join();
        var data = jsonDecode(json);
        result = data['origin'];
      } else {
        result =
            'Error getting IP address:\nHttp status ${response.statusCode}';
      }
    } catch (exception) {
      result = 'Failed getting IP address';
    }

    // If the widget was removed from the tree while the message was in flight,
    // we want to discard the reply rather than calling setState to update our
    // non-existent appearance.
  }

  @override
  Widget build(BuildContext context) {
    return Home();
  }
}

class Home extends StatelessWidget {
  Future<String> download() async {
    print("download start at ${DateTime.now()}");
    await new Future.delayed(const Duration(seconds: 5)); //当前线程休眠5秒
    print("download end at ${DateTime.now()}");
    return "download return";
  }

  void main() async {
    FlutterBoost.singleton
        .open('sample://flutter/myInvPage')
        .then((Map<dynamic, dynamic> value) {
      print(
          'call me when page is finished. did recieve second route result $value');
    });
  }

  /// 模拟异步加载用户信息
  Future _getUserInfo() async {
    await new Future.delayed(new Duration(milliseconds: 3000));
    return "我是用户";
  }

  @override
  Widget build(BuildContext context) {
    return StatefulBuilder(
        builder: (BuildContext context, StateSetter setState) {
      return Scaffold(
        resizeToAvoidBottomInset: false,
//        appBar: AppBar(
//          leading: IconButton(
//            icon: Icon(Icons.menu),
//            onPressed: () => debugPrint("Navigation button is pressed"),
//          ),
//          title: Text("首页"),
//          actions: <Widget>[
//            IconButton(
//              icon: Icon(Icons.search),
//              onPressed: () => debugPrint("Search button is pressed"),
//            ),
//          ],
//          bottom: TabBar(
//            tabs: [
//              Tab(icon: Icon(Icons.local_florist)),
//              Tab(icon: Icon(Icons.change_history)),
//              Tab(icon: Icon(Icons.directions_bike)),
//            ],
//            indicatorColor: Colors.white,
//          ),
//          backgroundColor: Color(0xffd43d3d),
//          elevation: 0,
//        ),
//        body: TabBarView(children: [
//          Icon(
//            Icons.local_florist,
//            size: 128,
//            color: Colors.black12,
//          ),
//          Icon(
//            Icons.change_history,
//            size: 128,
//            color: Colors.black12,
//          ),
//          Icon(
//            Icons.directions_bike,
//            size: 128,
//            color: Colors.black12,
//          ),
//        ]),
        body: Container(
          color: Colors.blueGrey,
          alignment: Alignment.center,
          child: GestureDetector(
            child: Text(
              "HomePage",
              style: TextStyle(fontSize: 20),
            ),
            onTap: () {
              main();
            },
          ),
        ),
      );
    });
  }
}
