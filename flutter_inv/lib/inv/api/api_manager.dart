import 'package:flutter_inv/inv/pages/page_inv/vo/inv_list_vo.dart';
import 'package:retrofit/retrofit.dart';
import 'package:dio/dio.dart';

part 'api_manager.g.dart';

@RestApi(baseUrl: "https://run.mocky.io")
abstract class RestClient {
  factory RestClient(Dio dio, {String baseUrl}) = _RestClient;

  @GET("/v3/e1bfcae7-babb-4f80-a599-1633dded2cd3")
  Future<InvListVo> getTasks();

}

