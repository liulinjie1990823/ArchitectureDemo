import 'package:flutter/material.dart';

class LoginModel extends ChangeNotifier {
  ValueNotifier<int> index = ValueNotifier(0);
  ValueNotifier<bool> canGetCode = ValueNotifier(false);
  CanLogin canLogin = CanLogin(false);
//  ValueNotifier<CanLoginModel> canLogin = ValueNotifier(CanLoginModel());

}

class CanLogin extends ValueNotifier<bool> {
  CanLogin(bool value) : super(value);
}
