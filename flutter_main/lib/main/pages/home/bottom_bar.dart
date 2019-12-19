import 'package:flutter/material.dart';

Color color = Color(0xff59c2ff);

class BottomBar extends StatefulWidget {
  final Function(int index) onChangeActiveTab;
  final int activeIndex;

  const BottomBar({Key key, this.onChangeActiveTab, this.activeIndex = 0})
      : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return BottomBarState();
  }
}

class BottomBarState extends State<BottomBar> {
  int _activeIndex;

  @override
  void initState() {
    super.initState();
    context.hashCode;
    _activeIndex = widget.activeIndex;
  }

  @override
  Widget build(BuildContext context) {
    double width = MediaQuery.of(context).size.width;
    double height = MediaQuery.of(context).size.height;
    double itemWidth = MediaQuery.of(context).size.width / 5;
    return Stack(
      alignment: Alignment.bottomCenter,
      children: <Widget>[
        //底部四个按钮
        Container(
          height: 50,
          color: Colors.white,
          child: Row(
            mainAxisSize: MainAxisSize.max,
            children: <Widget>[
              getBottomBarItem(
                width: itemWidth,
                index: 0,
                icon: Icons.home,
              ),
              getBottomBarItem(
                width: itemWidth,
                index: 1,
                icon: Icons.account_balance_wallet,
              ),
              Container(width: itemWidth),
              getBottomBarItem(
                width: itemWidth,
                index: 2,
                icon: Icons.notifications_none,
              ),
              getBottomBarItem(
                width: itemWidth,
                index: 3,
                icon: Icons.account_circle,
              ),
            ],
          ),
        ),
        //底部中间按钮
        Positioned(
          bottom: 10,
          height: 50,
          child: FloatingActionButton(
            elevation: 2,
            highlightElevation: 5,
            backgroundColor: color,
            child: Icon(
              Icons.add,
              size: 38,
            ),
            onPressed: () {},
          ),
        ),
      ],
    );
  }

  Widget getBottomBarItem({double width, int index, IconData icon}) {
    double iconSize = 30.0;
    return new Container(
      width: width,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          InkWell(
            child: Icon(
              icon,
              size: iconSize,
              color: _getItemColor(index: index),
            ),
            onTap: () {
              if (index != _activeIndex) {
                setState(() {
                  _activeIndex = index;
                  widget.onChangeActiveTab(index);
                });
              }
            },
          ),
//          Visibility(
//            visible: index == _activeIndex,
//            child: Container(
//              width: 10,
//              height: 2,
//              decoration: BoxDecoration(
//                color: color,
//                borderRadius: BorderRadius.circular(10),
//              ),
//            ),
//          ),
        ],
      ),
    );
  }

  Color _getItemColor({int index}) {
    return index == _activeIndex ? color : Colors.grey[300];
  }
}
