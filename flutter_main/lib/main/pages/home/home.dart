import 'package:flutter/material.dart';
import 'package:flutter_main/main/pages/home/bottom_bar.dart';

void main() => runApp(Home());

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'MediaQuery Demo',
      theme: new ThemeData(
        primarySwatch: Colors.red,
      ),
      home: HomePage(),
    );
  }
}

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    double width = MediaQuery.of(context).size.width;
    double height = MediaQuery.of(context).size.height;
    return Scaffold(
      backgroundColor: Colors.blueGrey,
      body: Container(
        width: width,
        height: height,
        child: Stack(
          children: <Widget>[
            Positioned(
              bottom: 0,
              width: width,
              height: 70,
              child: BottomBar(),
            ),
          ],
        ),
      ),
    );
  }
}
