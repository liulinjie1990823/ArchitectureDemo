import 'package:bloc/bloc.dart';
import 'package:flutter_login/login/repository/user_repository.dart';
import 'package:meta/meta.dart';

import 'login_event.dart';
import 'login_state.dart';

class LoginBloc extends Bloc<LoginEvent, LoginState> {
  final UserRepository userRepository;

  LoginBloc({@required this.userRepository}) : assert(userRepository != null);

  LoginState get initialState => LoginInitial();

  @override
  Stream<LoginState> mapEventToState(LoginEvent event) async* {
    if (event is LoginButtonPressed) {
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
    } else if (event is LoginTitleSelect) {}
  }
}
