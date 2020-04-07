import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_login/login/pages/page_coupon/coupon.dart';
import 'package:flutter_login/login/pages/page_mine/mine.dart';
import 'package:flutter_login/login/pages/page_strategy/strategy.dart';

class TabPage extends StatelessWidget {
  //页面
  final _pageList = [
    new HomePage(),
    new CouponPage(),
    new StrategyPage(),
    new MinePage(),
  ];
  int _tabIndex = 0;

  @override
  Widget build(BuildContext context) {
    return StatefulBuilder(
        builder: (BuildContext context, StateSetter setState) {
          return Scaffold(
            resizeToAvoidBottomInset: false,
            body: _pageList[_tabIndex],
            bottomNavigationBar: BottomNavigationBar(
              currentIndex: _tabIndex,
              type: BottomNavigationBarType.fixed,
              onTap: (int index) {
                setState(() => _tabIndex = index);
              },
              items: [
                BottomNavigationBarItem(
                  icon: Icon(Icons.explore),
                  title: Text("Explore"),
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.history),
                  title: Text("History"),
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.list),
                  title: Text("List"),
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.person),
                  title: Text("Person"),
                ),
              ],
            ),
          );
        });
  }
}

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
    String abc = await bar(); //当前线程休眠5秒
    print("abc at $abc ${DateTime.now()}");
    print("download end at ${DateTime.now()}");
  }

  void main() {
    print("main start at ${DateTime.now()}");
//    download(); //没有加await关键字表示当前线程不需要等待(不会阻塞)
    _getIPAddress();
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
    main();
    return Home();
  }
}

class Home extends StatelessWidget {
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
              child: Text(
                "HomePage",
                style: TextStyle(fontSize: 20),
              ),
            ),
          );
        });
  }
}
