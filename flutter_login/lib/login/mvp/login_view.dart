import 'package:flutter_base/mvp/mvp_view.dart';
import 'package:flutter_login/login/model/task.dart';
import 'package:flutter_login/login/pages/page_login/vo/login_vo.dart';

abstract class IGetTaskView extends IBaseTaskView1<List<Task>> {}
abstract class ILoginView extends IBaseTaskView1<LoginVo> {}
