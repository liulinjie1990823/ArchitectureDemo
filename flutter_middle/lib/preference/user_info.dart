import 'package:shared_preferences/shared_preferences.dart';

class UserInfo {

  int uid;
  String uname;
  String real_name;
  String phone;

}

class UserInfoPreference {


  UserInfoPreference() {
    Future<SharedPreferences> _prefs = SharedPreferences.getInstance();
  }

}