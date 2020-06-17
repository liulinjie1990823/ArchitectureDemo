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
class LoginPressedEvent extends AuthenticationEvent {
  final Map<String, dynamic> map;

  const LoginPressedEvent(this.map);
}

/// APP 退出登录事件
class LogoutEvent extends AuthenticationEvent {}
