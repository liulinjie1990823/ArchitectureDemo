import 'package:bloc/bloc.dart';
import 'package:flutter_login/login/repository/user_repository.dart';
import 'package:meta/meta.dart';

import 'login_event.dart';
import 'login_state.dart';

class AuthenticationBloc
    extends Bloc<AuthenticationEvent, AuthenticationState> {
  // 用户仓库
  final UserRepository userRepository;

  AuthenticationBloc({@required this.userRepository})
      : assert(userRepository != null);

  @override
  AuthenticationState get initialState => AuthenticationUninitialized();

  @override
  Stream<AuthenticationState> mapEventToState(
      AuthenticationEvent event) async* {
    if (event is AppStartEvent) {
      final bool hasToken = await userRepository.hasToken();
      if (hasToken) {
        yield AuthenticationAuthenticated();
      } else {
        yield AuthenticationUnauthenticated();
      }
    } else if (event is LoginEvent) {
      yield AuthenticationLoading();
      await userRepository.persistToken(event.token);
      yield AuthenticationAuthenticated();
    } else if (event is LogoutEvent) {
      yield AuthenticationLoading();
      await userRepository.deleteToken();
      yield AuthenticationUnauthenticated();
    }
  }
}

class LoginBloc extends Bloc<LoginEvent, LoginState> {
  /// 认证Bloc
  final AuthenticationBloc authenticationBloc;

  /// 用户信息仓库
  final UserRepository userRepository;

  LoginBloc({@required this.authenticationBloc, @required this.userRepository})
      : assert(authenticationBloc != null),
        assert(userRepository != null);

  LoginState get initialState => LoginInitial();

  @override
  Stream<LoginState> mapEventToState(LoginEvent event) async* {
    if (event is LoginPressedEvent) {
      yield LoginLoading();

      try {
        final token = await userRepository.authenticate(
          username: event.username,
          password: event.password,
        );

        yield LoginInitial();
      } catch (error) {
        yield LoginFailure(error: error.toString());
      }
    }
  }
}
