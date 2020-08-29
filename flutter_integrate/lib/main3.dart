import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or press Run > Flutter Hot Reload in a Flutter IDE). Notice that the
        // counter didn't reset back to zero; the application is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: InheritedWidgetTestRoute(),
    );
  }
}

class Item {
  String reference;

  Item(this.reference);
}

class ShareDataWidget extends InheritedWidget {
  ShareDataWidget({
    Key key,
    @required Widget child,
    @required this.data,
  }) : super(key: key, child: child);

  final MyInheritedWidgetState data;

  @override
  bool updateShouldNotify(ShareDataWidget oldWidget) {
    return true;
  }
  static ShareDataWidget of(BuildContext context) {
    return context.inheritFromWidgetOfExactType(ShareDataWidget);
  }
}

class MyInheritedWidget extends StatefulWidget {
  MyInheritedWidget({
    Key key,
    this.child,
  }) : super(key: key);

  final Widget child;

  @override
  MyInheritedWidgetState createState() => new MyInheritedWidgetState();

  static MyInheritedWidgetState of(BuildContext context) {
    return (context.inheritFromWidgetOfExactType(ShareDataWidget) as ShareDataWidget)
        .data;
  }
}

class MyInheritedWidgetState extends State<MyInheritedWidget> {
  /// List of Items
  List<Item> _items = <Item>[];

  /// Getter (number of items)
  int get itemsCount => _items.length;

  /// Helper method to add an Item
  void addItem() {
    setState(() {
    });
  }

  @override
  Widget build(BuildContext context) {
    return new ShareDataWidget(
      data: this,
      child: widget.child,
    );
  }
}

class InheritedWidgetTestRoute extends StatefulWidget {
  @override
  _InheritedWidgetTestRouteState createState() => new _InheritedWidgetTestRouteState();
}

class _InheritedWidgetTestRouteState extends State<InheritedWidgetTestRoute> {
  @override
  Widget build(BuildContext context) {
    return new MyInheritedWidget(
      child: new Scaffold(
        appBar: new AppBar(
          title: new Text('Title'),
        ),
        body: Builder(
          builder: (_) {
            return new Column(
              children: <Widget>[
                WidgetA(),
                new Container(
                  child: new RaisedButton(
                    child: new Text('Add Item'),
                    onPressed: () {
                      setState(() {

                      });
                    },
                  ),
                ),
                new Container(
                  child: new Row(
                    children: <Widget>[
                      new Icon(Icons.shopping_cart),
                      new WidgetB(),
                      Builder(builder: (_) {
                        return new WidgetC();
                      }),
                    ],
                  ),
                ),
              ],
            );
          },
        ),
      ),
    );
  }
}

class WidgetA extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print("WidgetA build");
    final MyInheritedWidgetState state = MyInheritedWidget.of(context);
    return new Container(
      child: new RaisedButton(
        child: new Text('Add Item'),
        onPressed: () {
          state.addItem();
        },
      ),
    );
  }
}

class WidgetB extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print("WidgetB build");
    final MyInheritedWidgetState state = MyInheritedWidget.of(context);
    return new Text('${state.itemsCount}');
  }
}

class WidgetC extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print("WidgetC build");
    return new Text('I am Widget C');
  }
}
