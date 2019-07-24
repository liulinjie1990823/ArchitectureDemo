import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  String imageUrl2 =
      "http://n.sinaimg.cn/sports/2_img/upload/4f160954/107/w1024h683/20181128/Yrxn-hpinrya6814381.jpg";

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  Widget setToolbar() {
    return Container(
      margin: EdgeInsets.fromLTRB(0, 20, 0, 0),
      height: 148,
      decoration: BoxDecoration(
        color: Colors.amber,
      ),
      child: Row(
        children: <Widget>[
          Image(image: NetworkImage(imageUrl2, scale: 8.5)),
          Expanded(child: Text("设置", textAlign: TextAlign.center)),
          Image(image: NetworkImage(imageUrl2, scale: 8.5)),
        ],
      ),
    );
  }

  Widget setDivider() {
    return Container(
      height: 1,
      color: Color(0xffeeeeee),
      margin: EdgeInsets.fromLTRB(20, 0, 20, 0),
    );
  }

  Widget setDivider2() {
    return Container(
      height: 10,
      color: Color(0xffF5F8FA),
    );
  }

  Widget setItem(String title, String dec) {
    return Container(
      height: 50,
      padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
      child: Row(
        children: <Widget>[
          Expanded(
              child: Container(
                  color: Colors.black12,
                  child: Text(title, textAlign: TextAlign.left))),
          Text(dec, textAlign: TextAlign.center),
          Container(
            color: Colors.grey,
            child: Image(
                fit: BoxFit.scaleDown,
                image:
                    AssetImage("assets/images/service_icon_right_arrow.png")),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Column(
        children: <Widget>[
          setToolbar(),
          CustomScrollView(
            slivers: <Widget>[
              SliverFixedExtentList(
                itemExtent: 50.0,
                delegate: SliverChildListDelegate([
                  setItem("环境切换", ""),
                  setDivider(),
                  setItem("我的资料", ""),
                  setDivider2(),
                  setItem("关联微信", ""),
                  setDivider2(),
                  setItem("清除缓存", "1000KB"),
                  setDivider(),
                  setItem("意见反馈", ""),
                  setDivider2(),
                  setItem("关于中国婚博会", "v6.12.0"),
                  setDivider(),
                  setItem("隐私政策", ""),
                  setDivider(),
                  setItem("推荐给好友", ""),
                  setDivider(),
                  setItem("官方微信", ""),
                  setDivider(),
                  setItem("联系客服", "400-365-520"),
                  setDivider(),
                ]),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
