import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_inv/inv/pages/page_inv/invitation.dart';

class InvRouter {
  static Map<String, PageBuilder> router = {
    'myInvPage': (String pageName, Map<String, dynamic> params, String _) {
      print('myInvPage params:$params');
      return MyInvListPage(params: params);
    }
  };
}
