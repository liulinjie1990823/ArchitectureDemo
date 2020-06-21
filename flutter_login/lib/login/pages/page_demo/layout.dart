import 'package:flutter/material.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/widgets/common_widget.dart';

class LayoutPage extends CommonTitleBarPage {
  @override
  Widget buildChild(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: <Widget>[
          title("容器布局"),
          wrapColumn(
            "Container",
            child: Container(
              color: Colors.red,
              height: 50,
            ),
          ),
          wrapColumn(
            "Container+Text",
            child: Container(
              color: Colors.red,
              height: 50,
              child: Text("data"),
            ),
          ),
          wrapColumn(
            "Container+Text+Alignment",
            child: Container(
              alignment: Alignment.center,
              color: Colors.red,
              height: 50,
              child: Text("data"),
            ),
          ),
          wrapColumn(
            "Card",
            child: Container(
              padding: EdgeInsets.all(20),
              width: double.infinity,
              height: 100.0,
              child: Card(
                shape: Border.all(
                  color: Colors.green,
                  width: 1,
                  style: BorderStyle.solid,
                ),
//                clipBehavior:
                elevation: 10,
                color: Colors.red,
                shadowColor: Colors.amber,
                child: Text('Hello World!'),
              ),
            ),
          ),
          title("宽高布局"),
          //外部宽高只能设置一个，比例才能起作用
          wrapColumn(
            "AspectRatio",
            child: Container(
              height: 100,
              color: Colors.green,
              child: AspectRatio(
                aspectRatio: 1.5,
                child: Container(
                  color: Colors.red,
                ),
              ),
            ),
          ),
          wrapColumn(
            "SizedBox+Card",
            child: SizedBox(
              width: 200.0,
              height: 100.0,
              child:
                  Card(shadowColor: Colors.amber, child: Text('Hello World!')),
            ),
          ),
          wrapColumn(
            "No SizedBox",
            child: Container(
              height: 100,
              padding: EdgeInsets.all(10),
              color: Colors.green,
              child: Container(
                color: Colors.red,
                child: Text('Hello World!'),
              ),
            ),
          ),
          wrapColumn(
            "SizedBox.expand+Text",
            child: Container(
              color: Colors.green,
              height: 100,
              padding: EdgeInsets.all(10),
              child: Container(
                color: Colors.red,
                child: SizedBox.expand(
                  child: Text('Hello World!'),
                ),
              ),
            ),
          ),
          wrapColumn(
            "width: double.infinity",
            child: Container(
              width: double.infinity,
              height: 100,
              padding: EdgeInsets.all(10),
              color: Colors.green,
              child: Container(
                color: Colors.red,
                child: Text('Hello World!'),
              ),
            ),
          ),
          wrapColumn(
            "Alignment.center",
            child: Container(
              height: 100,
              padding: EdgeInsets.all(10),
              color: Colors.green,
              child: Container(
                color: Colors.red,
                alignment: Alignment.center,
                child: Text('Hello World!'),
              ),
            ),
          ),
          wrapColumn(
            "no FractionallySizedBox",
            child: Container(
              color: Colors.green,
              height: 150.0,
              width: 150.0,
              padding: EdgeInsets.all(10.0),
              child: Container(
                color: Colors.red,
                child: Text('Hello World!'),
              ),
            ),
          ),
          wrapColumn(
            "FractionallySizedBox",
            child: Container(
              color: Colors.green,
              height: 150.0,
              width: 150.0,
              padding: EdgeInsets.all(10.0),
              child: FractionallySizedBox(
                alignment: Alignment.topLeft,
                widthFactor: 1.5,
                heightFactor: 0.5,
                child: Container(
                  color: Colors.red,
                  child: Text('Hello World!'),
                ),
              ),
            ),
          ),
          wrapColumn(
            "FractionallySizedBox",
            child: Container(
              color: Colors.green,
              height: 150.0,
              width: 150.0,
              child: FractionallySizedBox(
                alignment: Alignment.center,
                widthFactor: 1,
                heightFactor: 0.5,
                child: Card(
                    shadowColor: Colors.amber,
                    elevation: 0,
                    child: Text('Hello World!')),
              ),
            ),
          ),
          title("约束布局"),

          //UnconstrainedBox会
          wrapColumn(
            "UnconstrainedBox+LimitedBox",
            child: Container(
              width: double.infinity,
              height: 150,
              child: UnconstrainedBox(
                  child: LimitedBox(
                maxWidth: 100,
                maxHeight: 100,
                child: Container(
                  color: Colors.red,
                  width: 200,
                  height: 200,
                ),
              )),
            ),
          ),
          //防止布局无限绘制
          UnconstrainedBox(
              child: LimitedBox(
            maxWidth: 100,
            maxHeight: 100,
            child: Container(
              color: Colors.red,
              width: double.infinity,
              height: double.infinity,
            ),
          )),
          wrapColumn(
            "UnconstrainedBox",
            child: Container(
              width: double.infinity,
              height: 100,
              child: UnconstrainedBox(
                child: Container(color: Colors.red, width: 20, height: 50),
              ),
            ),
          ),
//          wrapColumn(
//            "UnconstrainedBox",
//            child: Container(
//              width: double.infinity,
//              height: 100,
//              child: UnconstrainedBox(
//                child: Container(color: Colors.red, width: 4000, height: 50),
//              ),
//            ),
//          ),
          wrapColumn(
            "ConstrainedBox",
            child: Container(
              width: double.infinity,
              height: 90,
              child: ConstrainedBox(
                constraints: BoxConstraints(
                  minWidth: 70,
                  minHeight: 70,
                  maxWidth: 150,
                  maxHeight: 150,
                ),
                child: Container(color: Colors.red, width: 10, height: 10),
              ),
            ),
          ),
          wrapColumn(
            "ConstrainedBox",
            child: Container(
              width: double.infinity,
              height: 90,
              child: Center(
                child: ConstrainedBox(
                  constraints: BoxConstraints(
                    minWidth: 70,
                    minHeight: 70,
                    maxWidth: 150,
                    maxHeight: 150,
                  ),
                  child: Container(color: Colors.red, width: 10, height: 10),
                ),
              ),
            ),
          ),
          wrapColumn(
            "ConstrainedBox",
            child: Container(
              width: double.infinity,
              height: 170,
              child: Center(
                  child: ConstrainedBox(
                constraints: BoxConstraints(
                  minWidth: 70,
                  minHeight: 70,
                  maxWidth: 150,
                  maxHeight: 150,
                ),
                child: Container(color: Colors.red, width: 1000, height: 1000),
              )),
            ),
          ),
          wrapColumn(
            "ConstrainedBox",
            child: Container(
              width: double.infinity,
              height: 170,
              child: Center(
                  child: ConstrainedBox(
                constraints: BoxConstraints(
                  minWidth: 70,
                  minHeight: 70,
                  maxWidth: 150,
                  maxHeight: 150,
                ),
                child: Container(color: Colors.red, width: 100, height: 100),
              )),
            ),
          ),

          wrapColumn(
            "OverflowBox",
            child: Container(
              width: 100,
              color: Colors.green,
              height: 100,
              padding: EdgeInsets.all(10),
              child: OverflowBox(
                alignment: Alignment.topLeft,
                minWidth: 0.0,
                minHeight: 0.0,
                maxWidth: double.infinity,
                maxHeight: double.infinity,
                child: Container(
                  color: Colors.red,
                  width: 200,
                  height: 50,
                  child: Image(
                    fit: BoxFit.cover,
                    image: AssetImage("assets/images/image_1.jpg"),
                  ),
                ),
              ),
            ),
          ),
          wrapColumn(
            "OverflowBox",
            child: Container(
              color: Colors.green,
              width: 100.0,
              height: 100.0,
              child: OverflowBox(
                minWidth: 0.0,
                minHeight: 0.0,
                maxWidth: double.infinity,
                maxHeight: double.infinity,
                child: Container(
                  color: Colors.red,
                  width: 200,
                  height: 50,
                ),
              ),
            ),
          ),
          wrapColumn(
            "OverflowBox",
            child: Container(
              color: Colors.green,
              width: 100.0,
              height: 100.0,
              padding: const EdgeInsets.all(10.0),
              child: OverflowBox(
                alignment: Alignment.topLeft,
                maxWidth: 150.0,
                maxHeight: 150.0,
                child: Container(
                  color: Color(0x33FF00FF),
                  width: 200.0,
                  height: 200.0,
                ),
              ),
            ),
          ),
          //SizedOverflowBox
          wrapColumn(
            "SizedOverflowBox",
            child: Container(
//              width: 300.0,
//              height: 300.0,
//              alignment: Alignment.center,
              color: Colors.green,
              padding: const EdgeInsets.all(10.0),
              child: SizedOverflowBox(
                alignment: Alignment.bottomLeft,
                size: Size(150, 150),
                child: Container(
                  color: Color(0x33FF00FF),
                  width: 200.0,
                  height: 50.0,
                ),
              ),
            ),
          ),
          title("边距布局"),
          wrapColumn(
            "Padding",
            child: Container(
              color: Colors.red,
              width: double.infinity,
              height: 50,
              child: Padding(
                padding: EdgeInsets.all(10),
                child: Text("data"),
              ),
            ),
          ),
          title("位置布局"),
          wrapColumn(
            "Align",
            child: Container(
              color: Colors.red,
              height: 50,
              child: Align(
                alignment: Alignment.bottomRight,
                child: Text("data"),
              ),
            ),
          ),
          wrapColumn(
            "Center",
            child: Container(
              color: Colors.red,
              width: double.infinity,
              height: 50,
              child: Center(
                child: Text("data"),
              ),
            ),
          ),
          wrapColumn(
            "Center",
            child: Container(
              color: Colors.red,
              width: double.infinity,
              height: 50,
              child: Center(
                child: Text("data"),
              ),
            ),
          ),
          title("多元素布局"),
          wrapColumn(
            "Row",
            child: Row(
              mainAxisSize: MainAxisSize.max,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Text("left"),
                Expanded(
                  child: Image(
                    image: AssetImage("assets/images/image_1.jpg"),
                    width: 100,
                    height: 100,
                  ),
                ),
                Container(
                    color: CommonColor.COMMON_MAIN_COLOR,
                    alignment: Alignment.bottomCenter,
                    child: Text("right")),
              ],
            ),
          ),
          wrapColumn(
            "Row+Expanded",
            child: Container(
              height: 50,
              width: 200,
              color: Colors.yellow,
              child:
                  Row(crossAxisAlignment: CrossAxisAlignment.center, children: [
                Expanded(
                    child: Container(
                        color: Colors.red,
                        child: Text(
                            'This is a very long text that won’t fit the line.'))),
                Container(color: Colors.green, child: Text('Goodbye!')),
              ]),
            ),
          ),
          wrapColumn(
            "Row+Expanded",
            child: Container(
              height: 50,
              width: 200,
              color: Colors.yellow,
              child: Row(children: [
                Expanded(
                  child: Container(
                      color: Colors.red,
                      child: Text(
                          "This is a very long text that won’t fit the line.")),
                ),
                Expanded(
                  child: Container(
                    color: Colors.green,
                    child: Text("Goodbye!"),
                  ),
                )
              ]),
            ),
          ),
          wrapColumn(
            "Row+Flexible",
            child: Container(
              height: 50,
              width: 200,
              color: Colors.yellow,
              child: Row(children: [
                Flexible(
                    child: Container(
                        color: Colors.red,
                        child: Text(
                            'This is a very long text that won’t fit the line.'))),
                Flexible(
                    child: Container(
                        color: Colors.green, child: Text("Goodbye!"))),
              ]),
            ),
          ),
//          wrapColumn(
//            "Row",
//            child: Container(
//              height: 50,
//              width: 200,
//              color: Colors.yellow,
//              child:
//                  Row(crossAxisAlignment: CrossAxisAlignment.center, children: [
//                Container(
//                    color: Colors.red,
//                    child: Text(
//                        'This is a very long text that won’t fit the line.')),
//                Container(color: Colors.green, child: Text('Goodbye!')),
//              ]),
//            ),
//          ),
          title("其他布局"),
          wrapColumn(
            "IntrinsicHeight",
            child: IntrinsicHeight(
              child: Row(
                mainAxisSize: MainAxisSize.max,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  Text("left"),
                  Expanded(
                    child: Image(
                      image: AssetImage("assets/images/image_1.jpg"),
                      width: 100,
                      height: 100,
                    ),
                  ),
                  Align(
                    alignment: Alignment.bottomCenter,
                    child: Text("right"),
                  ),
                ],
              ),
            ),
          ),
          wrapColumn(
            "no FittedBox",
            child: new Container(
              color: Colors.amberAccent,
              width: 100.0,
              height: 100.0,
              child: Text("FittedBox"),
            ),
          ),
          wrapColumn(
            "FittedBox+cover",
            child: new Container(
              color: Colors.amberAccent,
              width: 100.0,
              height: 100.0,
              child: FittedBox(
                fit: BoxFit.cover,
                alignment: Alignment.topLeft,
                child: Text("FittedBox"),
              ),
            ),
          ),
          wrapColumn(
            "FittedBox+contain",
            child: new Container(
              color: Colors.amberAccent,
              width: 100.0,
              height: 100.0,
              child: FittedBox(
                fit: BoxFit.contain,
                alignment: Alignment.center,
                child: Container(
                  color: Colors.red,
                  child: Text("FittedBox"),
                ),
              ),
            ),
          ),
          wrapColumn(
            "FittedBox+scaleDown",
            child: new Container(
              color: Colors.amberAccent,
              width: 100.0,
              height: 100.0,
              child: FittedBox(
                fit: BoxFit.scaleDown,
                alignment: Alignment.center,
                child: Container(
                  color: Colors.red,
                  child: Text("FittedBox"),
                ),
              ),
            ),
          ),
          wrapColumn(
            "FittedBox+cover",
            child: Container(
              color: Colors.amberAccent,
              width: 100.0,
              height: 100.0,
              child: FittedBox(
                fit: BoxFit.cover,
                alignment: Alignment.center,
                child: Image(
                  image: AssetImage("assets/images/image_1.jpg"),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget title(String text) {
    return Container(
      color: Colors.blue,
      alignment: Alignment.centerLeft,
      child: Text(
        text,
        style: TextStyle(fontSize: 30),
      ),
    );
  }

  Widget wrapColumn(String text, {Widget child}) {
    return Column(
      children: <Widget>[
        Container(
          color: Colors.grey,
          alignment: Alignment.centerLeft,
          child: Text(
            text,
            style: TextStyle(fontSize: 20),
          ),
        ),
        child
      ],
    );
  }

  @override
  String centerWidgetText(BuildContext context) {
    return "Layout";
  }
}
