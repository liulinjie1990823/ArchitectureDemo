import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider<MyModel>(
          create: (context) => MyModel(),
        )
      ],
      child: MaterialApp(
        home: Scaffold(
          appBar: AppBar(title: Text('My App')),
          body: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Container(
                  padding: const EdgeInsets.all(20),
                  color: Colors.green[200],
                  child: Builder(
                    builder: (context) {
                      return RaisedButton(
                        child: Text('Do something' +
                            Provider.of<MyModel>(context, listen: true)
                                .someValue),
                        onPressed: () {
                          Provider.of<MyModel>(context, listen: true)
                              .doSomething();
                        },
                      );
                    },
                  )),
              Container(
                padding: const EdgeInsets.all(35),
                color: Colors.blue[200],
                child: Builder(
                  //                    <--- Consumer
                  builder: (context) {
                    return Text(
                        Provider.of<MyModel>(context, listen: true).someValue);
                  },
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class MyModel with ChangeNotifier {
  //                          <--- MyModel
  String someValue = 'Hello';

  void doSomething() {
    someValue = 'Goodbye';
    print(someValue);
    notifyListeners();
  }
}
