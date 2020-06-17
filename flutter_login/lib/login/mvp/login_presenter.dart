import 'package:flutter_base/mvp/mvp_view.dart';
import 'package:flutter_login/login/model/task.dart';
import 'package:flutter_login/login/mvp/login_view.dart';
import 'package:flutter_login/login/pages/page_login/vo/login_vo.dart';
import 'package:flutter_middle/api/api_manager.dart';

import 'package:flutter_login/login/api/api_manager.dart';

class LoginPresenter {
  void login(ILoginView view) {
    if (view.getParams1(1) == null) {
      return null;
    }
    _client.login(view.getParams1(1)).then((LoginVo value) {
      if (value.code == 0) {
        view.onDataSuccess1(value, 1);
      }
    });
  }

  RestClient _client;

  LoginPresenter() {
    _client = RestClient(ApiManager.dio());
  }
}
