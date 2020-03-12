import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

//验证事件
abstract class AuthenticationEvent extends Equatable {
  const AuthenticationEvent();

  @override
  List<Object> get props => [];
}

/// APP 启动事件
class AppStartEvent extends AuthenticationEvent {}

/// 登录事件
class LoginEvent extends AuthenticationEvent {
  final String token;

  const LoginEvent({@required this.token});

  @override
  List<Object> get props => [token];

  @override
  String toString() => "LoginEvent { token: $token }";
}

/// APP 退出登录事件
class LogoutEvent extends AuthenticationEvent {}

class LoginPressedEvent extends LoginEvent {
  final String username;
  final String password;

  const LoginPressedEvent({
    @required this.username,
    @required this.password,
  });

  @override
  List<Object> get props => [username, password];

  @override
  String toString() =>
      'LoginPressedEvent { username: $username, password: $password }';
}
