import 'package:flutter/material.dart';
import 'package:flutter_main/main/pages/page_coupon/coupon.dart';
import 'package:flutter_main/main/pages/page_home/home.dart';
import 'package:flutter_main/main/pages/page_inv/invitation.dart';
import 'package:flutter_main/main/pages/page_mine/mine.dart';
import 'package:flutter_main/main/pages/page_strategy/strategy.dart';

class TabPage extends StatelessWidget {
  //页面
  final _pageList = [
    HomePage(),
    CouponPage(),
    StrategyPage(),
    MinePage(),
    InvHomePage(),
  ];
  int _tabIndex = 4;

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
            BottomNavigationBarItem(
              icon: Icon(Icons.add_a_photo),
              title: Text("Inv"),
            ),
          ],
        ),
      );
    });
  }
}
