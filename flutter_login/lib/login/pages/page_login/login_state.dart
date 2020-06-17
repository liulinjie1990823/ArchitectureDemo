import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

/// 认证状态
abstract class AuthenticationState extends Equatable {
  @override
  List<Object> get props => [];
}

/// - uninitialized - 身份验证未初始化，跳闪屏页
class AuthenticationUninitialized extends AuthenticationState {}

/// - loading - 等待保存/删除Token
class AuthenticationLoading extends AuthenticationState {}

/// - authenticated - 认证成功
class AuthenticationAuthenticated extends AuthenticationState {}

/// - unauthenticated - 未认证
class AuthenticationUnauthenticated extends AuthenticationState {}

//abstract class LoginState extends Equatable {
//  const LoginState();
//
//  @override
//  List<Object> get props => [];
//}
//
//class LoginInitial extends LoginState {}
//
//class LoginLoading extends LoginState {}
//
//class LoginFailure extends LoginState {
//  final String error;
//
//  const LoginFailure({@required this.error});
//
//  @override
//  List<Object> get props => [error];
//
//  @override
//  String toString() => 'LoginFailure { error: $error }';
//}
