import 'package:scoped_model/scoped_model.dart';

class CounterModel extends Model {
  int _index = 0;

  bool _canLogin = false;
  bool _canGetCode = false;

  int get index => _index;

  set index(int value) {
    _index = value;

    notifyListeners();
  }

  bool get canLogin => _canLogin;

  set canLogin(bool value) {
    _canLogin = value;
    notifyListeners();
  }

  bool get canGetCode => _canGetCode;

  set canGetCode(bool value) {
    _canGetCode = value;
    notifyListeners();
  }

  static CounterModel of(context, [bool rebuild = true]) {
    ScopedModel.of<CounterModel>(context, rebuildOnChange: rebuild);
  }
}
