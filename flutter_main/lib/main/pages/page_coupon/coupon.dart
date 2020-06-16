import 'package:flutter/material.dart';
import 'package:flutter_middle/configs/common_color.dart';

class CouponPage extends StatelessWidget {
  Widget _title({double height}) {
    return Container(
      width: double.infinity,
      decoration: BoxDecoration(color: CommonColor.COMMON_MAIN_COLOR),
      child: Stack(
        children: <Widget>[
          Positioned(
              left: 15,
              child: Text(
                "返回",
                style: TextStyle(fontSize: 15, color: Colors.white),
              )),
          Container(
            width: 150,
            height: 100,
            child: Align(
              child: Text(
                "我的请柬",
                style: TextStyle(fontSize: 17, color: Colors.white),
              ),
            ),
          ),
          Positioned(
              bottom: 0,
              right: 15,
              child: Text(
                "意见反馈",
                style: TextStyle(fontSize: 15, color: Colors.white),
              )),
        ],
        alignment: Alignment.center,
        fit: StackFit.loose,
      ),
    );
  }

  Widget _title2({double height}) {
    return Container(
      constraints:
          BoxConstraints.expand(width: double.infinity, height: height),
      decoration: BoxDecoration(color: CommonColor.COMMON_MAIN_COLOR),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Center(child: Text("返回")),
          Expanded(
            child: Container(
              height: 200,
              child: Center(child: Text("我的请柬")),
            ),
          ),
          Expanded(
              child: Column(
            children: <Widget>[
              Expanded(child: Text("意见反馈")),
            ],
          )),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          color: Colors.brown,
          alignment: Alignment.center,
          child: Column(
            children: <Widget>[
              _title(height: 100),
              _title2(height: 300),
            ],
          ),
        ),
      ),
    );
  }
}
