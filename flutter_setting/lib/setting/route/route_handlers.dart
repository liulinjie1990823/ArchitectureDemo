import 'package:fluro/fluro.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_setting/setting/pages/page_profile/profile.dart';

var profilePageHandler = Handler(
    handlerFunc: (BuildContext context, Map<String, List<String>> params) {
  return ProfilePage();
});
