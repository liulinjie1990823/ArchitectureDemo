import 'package:flutter/material.dart';
import 'package:flutter_middle/widgets/common_widget.dart';


class OffstagePage extends CommonPage{

  @override
  bool useSafeArea(BuildContext context) {
    return true;
  }
  @override
  Widget buildChild(BuildContext context) {
    return OffstageDemo();
  }
}

class OffstageDemo extends StatefulWidget{
  @override
  _OffstageDemoState createState() {
    return _OffstageDemoState();
  }
}

class _OffstageDemoState extends State<OffstageDemo>{
  bool _isShow = false;
  bool _visible = true;
  bool _saveSpace = false;
  bool _isInteractive = false;
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return SingleChildScrollView(
      child: Column(
        children: <Widget>[
          SwitchListTile(
            value: _isShow,
            title: Text('Offstage(是否隐藏)'),
            onChanged: (val) => setState(() => _isShow = !_isShow),
          ),
          Offstage(
            offstage: _isShow,
            child: Container(
              constraints: BoxConstraints.tight(Size(200.0, 100.0)),
              margin: EdgeInsets.symmetric(vertical: 40.0),
              color: Colors.blue,
            ),
          ),
          Container(
            constraints: BoxConstraints.tight(Size(200.0, 100.0)),
            color: Colors.yellow,
            margin: EdgeInsets.only(bottom: 40.0),
          ),
          Divider(),
          SwitchListTile(
            value: _visible,
            title: Text('Visibility(是否可见)'),
            onChanged: (val) => setState(() => _visible = val),
          ),
          SwitchListTile(
            title: Text('是否占用空间'),
            value: _saveSpace,
            onChanged: (val) => setState((){
              _saveSpace = val;
              // ignore: unnecessary_statements
              !_saveSpace && (_isInteractive = false);
            }),
          ),
          SwitchListTile(
            title: Text('隐藏后是否响应事件'),
            value: _isInteractive,
            onChanged: (val) => setState((){
              _isInteractive = val;
              // ignore: unnecessary_statements
              _isInteractive && (_saveSpace = true);
            }),
          ),
          Visibility(
            visible: _visible,
            maintainState: true,
            maintainAnimation: true,
            maintainSize: _saveSpace,
            maintainInteractivity: _isInteractive,
            child: GestureDetector(
              child: Container(
                constraints: BoxConstraints.tight(Size(200.0, 100.0)),
                margin: EdgeInsets.symmetric(vertical: 40.0),
                color: Colors.blue,
              ),
              onTap: () {
                print('tap');
              },
            ),
          ),
          Container(
            constraints: BoxConstraints.tight(Size(200.0, 100.0)),
            color: Colors.yellow,
            margin: EdgeInsets.only(bottom: 40.0),
          ),
        ],
      ),
    );
  }
}