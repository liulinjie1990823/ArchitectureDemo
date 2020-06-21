import 'package:flutter/material.dart';
import 'package:flutter_inv/inv/pages/page_inv/invitation.dart';
import 'package:flutter_inv/inv/pages/page_template/template.dart';
import 'package:flutter_main/main/pages/page_coupon/coupon.dart';
import 'package:flutter_main/main/pages/page_home/home.dart';
import 'package:flutter_main/main/pages/page_mine/mine.dart';
import 'package:flutter_main/main/pages/page_strategy/strategy.dart';
import 'package:flutter_middle/widgets/common_widget.dart';

class TabPage extends StatelessWidget {
  //页面
  final _pageList = [
    HomePage(),
    CouponPage(),
    MinePage(),
    TemplateHomePage(),
    MyInvListPage(),
  ];

  final _bottomNavigationBarItemList = [
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
  ];
  static int _tabIndex = 0;

  var _pageController = PageController(
    initialPage: _tabIndex,
    viewportFraction: 1,
  );

  @override
  Widget build(BuildContext context) {
    return StatefulBuilder(
        builder: (BuildContext context, StateSetter setState) {
      return Scaffold(
        resizeToAvoidBottomInset: false,
        body: PageView.builder(
          physics: NeverScrollableScrollPhysics(),
          controller: _pageController,
          onPageChanged: (int index) {
            setState(() => _tabIndex = index);
          },
          //回调函数
          itemCount: _pageList.length,
          itemBuilder: (context, index) => _pageList[index],
        ),
        bottomNavigationBar: BottomNavigationBar(
          currentIndex: _tabIndex,
          type: BottomNavigationBarType.fixed,
          onTap: (int index) {
            _pageController.jumpToPage(index);
//            _pageController.animateToPage(
//              index,
//              duration: Duration(milliseconds: 300),
//              curve: Curves.linear,
//            );
          },
          items: _bottomNavigationBarItemList,
        ),
      );
    });
  }
}
