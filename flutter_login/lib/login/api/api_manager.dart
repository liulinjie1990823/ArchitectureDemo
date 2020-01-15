import 'package:dio/dio.dart' hide Headers;
import 'package:flutter_login/login/model/task.dart';
import 'package:flutter_login/login/mvp/login_mvp_view.dart';
import 'package:flutter_middle/api/api_manager.dart';
import 'package:retrofit/retrofit.dart';

@RestApi(baseUrl: "https://5d42a6e2bc64f90014a56ca0.mockapi.io/api/v1/")
abstract class LoginApiService extends MiddleRestClient {
  @GET("/tasks")
  Future<List<Task>> getTasks(IGetTaskView view);

  @GET("/tasks/{id}")
  Future<Task> getTask(@Path("id") String id);
}

class LoginApiServiceImpl extends LoginApiService {
  Dio dio;
  String baseUrl = "https://5d42a6e2bc64f90014a56ca0.mockapi.io/api/v1/";

  LoginApiServiceImpl(this.dio,
      {this.baseUrl = "https://5d42a6e2bc64f90014a56ca0.mockapi.io/api/v1/"}) {
    print(this.baseUrl);
  }

  @override
  Future<Task> getTask(String id) async {
    return null;
  }

  @override
  Future<List<Task>> getTasks(IGetTaskView view) async {
    final Response<List<dynamic>> _result = await dio.request("/tasks",
        queryParameters: view.getParams1(view.hashCode),
        options: RequestOptions(baseUrl: baseUrl));
    var value = _result.data
        .map((dynamic i) => Task.fromJson(i as Map<String, dynamic>))
        .toList();
    return value;
  }
}
