import 'package:flutter_login/login/pages/page_login/vo/login_vo.dart';
import 'package:flutter_middle/api/api_manager.dart';
import 'package:meta/meta.dart';
import 'package:flutter_middle/local/local_storage.dart';
import 'package:flutter_middle/config/sp_config.dart';
import 'package:flutter_login/login/api/api_manager.dart';

class UserRepository {
  RestClient _client;

  UserRepository() {
    _client = RestClient(ApiManager.dio());
  }

  Future<String> authenticate({
    @required String username,
    @required String password,
  }) async {
    await Future.delayed(Duration(seconds: 1));
    return 'token';
  }

  Future<void> deleteToken() async {
    /// delete from keystore/keychain
    await await LocalStorage.remove(SpConfig.KEY_TOKEN);
    return;
  }

  Future<void> saveToken(String token) async {
    await await LocalStorage.save(SpConfig.KEY_TOKEN, token);
    return;
  }

  //
  Future<LoginVo> login(Map<String, dynamic> map) {
    return _client.login(map);
  }

  //
  Future<bool> hasToken() async {
    String token = await LocalStorage.get(SpConfig.KEY_TOKEN);
    return token != null && token.length > 0;
  }
}
