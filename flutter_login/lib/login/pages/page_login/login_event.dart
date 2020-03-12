import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

//验证事件
abstract class AuthenticationEvent extends Equatable {
  const AuthenticationEvent();

  @override
  List<Object> get props => [];
}

/// APP 登录事件
class Login extends AuthenticationEvent {
  final String token;

  const Login({@required this.token});

  @override
  List<Object> get props => [token];

  @override
  String toString() => "LoggedIn { token: $token }";
}

/// APP 启动事件
class AppStart extends AuthenticationEvent {}

/// APP 退出登录事件
class Logout extends AuthenticationEvent {}

/// 登录事件
abstract class LoginEvent extends Equatable {
  const LoginEvent();
}

class LoginButtonPressed extends LoginEvent {
  final String username;
  final String password;

  const LoginButtonPressed({
    @required this.username,
    @required this.password,
  });

  @override
  List<Object> get props => [username, password];

  @override
  String toString() =>
      'LoginButtonPressed { username: $username, password: $password }';
}

class LoginTitleSelect extends LoginEvent {
  final int index;

  const LoginTitleSelect({@required this.index});

  @override
  List<Object> get props => [index];
}
