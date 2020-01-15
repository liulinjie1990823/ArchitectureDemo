class IView {}

abstract class IBaseView {
  void onDataError(int tag, Exception exception, int taskId);
}

abstract class IBaseTaskView1<T> extends IBaseView {
  Map<String, String> getParams1(int taskId);

  void onDataSuccess1(T result, int taskId);
}

abstract class IBaseTaskView2<T> extends IBaseView {
  Map<String, String> getParams2(int taskId);

  void onDataSuccess2(T result, int taskId);
}

abstract class IBaseTaskView3<T> extends IBaseView {
  Map<String, String> getParams3(int taskId);

  void onDataSuccess3(T result, int taskId);
}

abstract class IBaseTaskView4<T> extends IBaseView {
  Map<String, String> getParams4(int taskId);

  void onDataSuccess4(T result, int taskId);
}

abstract class IBaseTaskView5<T> extends IBaseView {
  Map<String, String> getParams5(int taskId);

  void onDataSuccess5(T result, int taskId);
}

abstract class IBaseTaskView6<T> extends IBaseView {
  Map<String, String> getParams6(int taskId);

  void onDataSuccess6(T result, int taskId);
}

abstract class IBaseTaskView7<T> extends IBaseView {
  Map<String, String> getParams7(int taskId);

  void onDataSuccess7(T result, int taskId);
}

abstract class IBaseTaskView8<T> extends IBaseView {
  Map<String, String> getParams8(int taskId);

  void onDataSuccess8(T result, int taskId);
}

abstract class IBaseTaskView9<T> extends IBaseView {
  Map<String, String> getParams9(int taskId);

  void onDataSuccess9(T result, int taskId);
}
