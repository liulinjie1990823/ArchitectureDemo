import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';

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
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: Center(child: Text("HomePage")),
    );
  }
}
