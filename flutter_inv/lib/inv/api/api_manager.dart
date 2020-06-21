import 'package:flutter_inv/inv/pages/page_inv/vo/inv_list_vo.dart';
import 'package:retrofit/retrofit.dart';
import 'package:dio/dio.dart';

part 'api_manager.g.dart';

@RestApi(baseUrl: "http://open.test.jiehun.com.cn")
abstract class InvRestClient {
  factory InvRestClient(Dio dio, {String baseUrl}) = _InvRestClient;

  @GET("/inv/invitation/get-invitate-list")
  Future<InvListVo> getInvitations();

}

