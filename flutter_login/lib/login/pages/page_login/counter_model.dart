import 'package:scoped_model/scoped_model.dart';

class CounterModel extends Model {
  int _index = 0;

  bool _ok = true;

  int get index => _index;

  set index(int value) {
    _index = value;

    notifyListeners();
  }

  bool get ok => _ok;

  set ok(bool value) {
    _ok = value;
  }

  static CounterModel of(context) =>
      ScopedModel.of<CounterModel>(context, rebuildOnChange: true);
}
