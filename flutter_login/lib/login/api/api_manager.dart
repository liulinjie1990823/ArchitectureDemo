import 'package:flutter_login/login/pages/page_login/vo/login_vo.dart';
import 'package:retrofit/retrofit.dart';
import 'package:dio/dio.dart';

part 'api_manager.g.dart';

@RestApi(baseUrl: "http://open.test.jiehun.com.cn")
abstract class RestClient {
  factory RestClient(Dio dio, {String baseUrl}) = _RestClient;

  @POST("/user/account/get-login")
  Future<LoginVo> login(@Body() Map<String, dynamic> map);



}

