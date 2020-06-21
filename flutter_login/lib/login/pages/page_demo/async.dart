import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter_middle/widgets/common_widget.dart';

class AsyncPage extends CommonTitleBarPage {
  @override
  Widget buildChild(BuildContext context) {
    return ListView(
      children: <Widget>[
        Column(
          children: <Widget>[
            FlatButton(
              onPressed: () {
                main1();
              },
              child: Container(
                height: 50,
                child: Text("main"),
              ),
            )
          ],
        )
      ],
    );
  }

  void main1() {
    void download() async {
      print("download start at ${DateTime.now()}");
      await new Future.delayed(const Duration(seconds: 5)); //当前线程休眠5秒
      print("download end at ${DateTime.now()}");
    }

    print("main start at ${DateTime.now()}");
    download(); //没有加await关键字表示当前线程不需要等待(不会阻塞)
    print("main end at ${DateTime.now()}");
  }

  void main2() async {
    void download() async {
      print("download start at ${DateTime.now()}");
      await new Future.delayed(const Duration(seconds: 5)); //当前线程休眠5秒
      print("download end at ${DateTime.now()}");
    }

    print("main start at ${DateTime.now()}");
    await download(); //等待await操作
    print("main end at ${DateTime.now()}");
  }

  download() async {
    //声明async的函数，返回类型必须是Future类型。
    print("download start at ${DateTime.now()}");
    await new Future.delayed(const Duration(seconds: 5)); //当前线程休眠5秒
    print("download end at ${DateTime.now()}");
    return "Hi,I come from server!"; //自动转成Future<Sring>类型
  }

  @override
  String centerWidgetText(BuildContext context) {
    return "AsyncPage";
  }
}
