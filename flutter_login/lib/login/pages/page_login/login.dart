import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_login/login/model/task.dart';
import 'package:flutter_login/login/mvp/login_mvp_view.dart';
import 'package:flutter_login/login/pages/page_login/counter_model.dart';
import 'package:flutter_login/login/repository/user_repository.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/utils/color_util.dart';
import 'package:flutter_middle/utils/display_util.dart';
import 'package:flutter_middle/widgets/common_widget.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
import 'package:flutter_middle/store/store.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  final LoginModel model;

  const MyApp({Key key, @required this.model}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: LoginPage(),
    );
  }
}

class LoginViewModel extends ChangeNotifier {
  final UserRepository userRepository = UserRepository();

  int _tabIndex = 0;
  int _getCodeStatus = 0; //1可用状态，2倒计时状态
  int _forgetPwdStatus = 0;
  bool _canLogin = false;

  int get getCodeStatus => _getCodeStatus;

  set getCodeStatus(int value) {
    _getCodeStatus = value;
    notifyListeners();
  }

  int get tabIndex => _tabIndex;

  set tabIndex(int value) {
    _tabIndex = value;
    notifyListeners();
  }

  bool get canLogin => _canLogin;

  set canLogin(bool value) {
    _canLogin = value;
    notifyListeners();
  }

  int get forgetPwdStatus => _forgetPwdStatus;

  set forgetPwdStatus(int value) {
    _forgetPwdStatus = value;
    notifyListeners();
  }
}

class LoginPage extends StatelessWidget {
  LoginViewModel _model;

  @override
  Widget build(BuildContext context) {
    print("LoginPage build");
    _model = new LoginViewModel();
    return Store.init(_model, child: _LoginPage());
  }
}

class _LoginPage extends CommonPage implements IGetTaskView {
  final TextEditingController _accountController = new TextEditingController();
  final TextEditingController _pwdController = new TextEditingController();
  final TextEditingController _codeController = new TextEditingController();

  @override
  bool statusBarTextWhite() {
    return false;
  }

  _checkEnableLogin(LoginViewModel model) {
    if (_accountController.text.length == 11) {
      if (model.getCodeStatus == 0) {
        model.getCodeStatus = 1;
      }
      if (model.forgetPwdStatus == 0) {
        model.forgetPwdStatus = 1;
      }
    } else {
      model.getCodeStatus = 0;
      model.forgetPwdStatus = 0;
    }
    if (model.tabIndex == 0) {
      //验证码登录
      if (_codeController.text.length == 4 &&
          _accountController.text.length == 11) {
        model.canLogin = true;
      } else {
        model.canLogin = false;
      }
    } else {
      //密码登录
      if (_pwdController.text.length >= 6 &&
          _accountController.text.length == 11) {
        model.canLogin = true;
      } else {
        model.canLogin = false;
      }
    }
  }

  // 轮播
  Widget _banner() {
    return Swiper(
      itemBuilder: (BuildContext context, int index) {
        print("Login Swiper Image build");
        return Image(
          fit: BoxFit.fitWidth,
          alignment: Alignment.topCenter, //可以控制Image中图片的位置
          image: AssetImage("assets/images/login_guide_img_one.png"),
        );
      },
      itemCount: 4,
      pagination: SwiperPagination(),
      autoplay: false,
      control: SwiperControl(
        iconPrevious: null,
        iconNext: null,
      ),
    );
  }

  //切换登录方式tab
  Widget _loginMethodSwitch() {
    return Builder(
      builder: (BuildContext context) {
        return Container(
          child: Row(
            children: <Widget>[
              Expanded(
                flex: 1,
                child: GestureDetector(
                  onTap: () {
                    LoginViewModel model = Store.read<LoginViewModel>(context);
                    model.tabIndex = 0;
                  },
                  child: Store.selector<LoginViewModel, int>(
                    shouldRebuild: (previous, next) {
                      return previous != next;
                    },
                    selector: (_, LoginViewModel model) {
                      return model.tabIndex;
                    },
                    builder: (context, int value, child) {
                      print("验证码登录tab build");
                      return Padding(
                        padding: const EdgeInsets.symmetric(vertical: 20),
                        child: Text(
                          "验证码登录",
                          style: TextStyle(
                            fontSize: 19,
                            color: value == 0
                                ? Color(CommonColor.C_NORMAL_TEXT)
                                : Color(CommonColor.C_UN_ENABLE_TEXT),
                            fontStyle: FontStyle.normal,
                            fontWeight: FontWeight.w600,
                            decoration: TextDecoration.none,
                          ),
                          textAlign: TextAlign.center,
                        ),
                      );
                    },
                  ),
                ),
              ),
              Container(
                constraints: BoxConstraints.expand(
                    width: 1 / DisplayUtil.pixelRatio, height: 16),
                alignment: Alignment.center,
                decoration: BoxDecoration(
                  color: Color(CommonColor.C_DIVIDE),
                ),
              ),
              Expanded(
                flex: 1,
                child: GestureDetector(
                  onTap: () {
                    LoginViewModel model = Store.read<LoginViewModel>(context);
                    model.tabIndex = 1;
                  },
                  child: Store.selector<LoginViewModel, int>(
                    shouldRebuild: (previous, next) {
                      return previous != next;
                    },
                    selector: (_, LoginViewModel model) {
                      return model.tabIndex;
                    },
                    builder: (context, int value, child) {
                      print("密码登录tab build");
                      return Padding(
                        padding: const EdgeInsets.symmetric(vertical: 20),
                        child: Text(
                          "密码登录",
                          style: TextStyle(
                            fontSize: 19,
                            color: value == 1
                                ? Color(CommonColor.C_NORMAL_TEXT)
                                : Color(CommonColor.C_UN_ENABLE_TEXT),
                            fontStyle: FontStyle.normal,
                            fontWeight: FontWeight.w600,
                            decoration: TextDecoration.none,
                          ),
                          textAlign: TextAlign.center,
                        ),
                      );
                    },
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  //输入手机号
  Widget _accountInput() {
    return Builder(
      builder: (BuildContext context) {
        return Container(
          constraints:
              BoxConstraints.expand(width: double.infinity, height: 50),
          child: TextField(
            expands: true,
            autofocus: false,
            maxLines: null,
            textAlign: TextAlign.start,
            textAlignVertical: TextAlignVertical.center,
            controller: _accountController,
            keyboardType: TextInputType.number,
            inputFormatters: [LengthLimitingTextInputFormatter(11)],
            //输入style
            style: TextStyle(
              fontSize: 16,
              textBaseline: TextBaseline.alphabetic,
            ),
            //输入装饰
            decoration: InputDecoration(
              contentPadding: EdgeInsets.only(top: 0, bottom: 0),
              hintText: "请输入手机号",
            ),
            onChanged: (text) {
              print("account onChanged:$text");
              _checkEnableLogin(Store.read<LoginViewModel>(context));
            },
          ),
        );
      },
    );
  }

  //输入验证码
  Widget _codeInputLayout() {
    return Store.selector<LoginViewModel, int>(
      shouldRebuild: (previous, next) {
        return previous != next;
      },
      selector: (_, LoginViewModel model) {
        return model.tabIndex;
      },
      builder: (context, int value, child) {
        print("请输入短信验证码layout build");
        return Visibility(
          visible: value == 0 ? true : false,
          child: Container(
            margin: EdgeInsets.only(top: 12),
            constraints:
                BoxConstraints.expand(width: double.infinity, height: 50),
            child: Row(
              children: <Widget>[
                Expanded(
                  child: TextField(
                    textAlign: TextAlign.start,
                    textAlignVertical: TextAlignVertical.center,
                    controller: _codeController,
                    keyboardType: TextInputType.number,
                    inputFormatters: [LengthLimitingTextInputFormatter(4)],
                    style: TextStyle(
                      fontSize: 16,
                      textBaseline: TextBaseline.alphabetic,
                    ),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.zero,
                      hintText: "请输入短信验证码",
                    ),
                    onChanged: (text) {
                      print("Verification code onChanged:$text");
                      _checkEnableLogin(Store.read<LoginViewModel>(context));
                    },
                  ),
                ),
                GestureDetector(
                  onTap: () {
                    LoginViewModel model = Store.read<LoginViewModel>(context);
                    model.getCodeStatus = 2;
                  },
                  child: Store.selector<LoginViewModel, int>(
                    shouldRebuild: (previous, next) {
                      return previous != next;
                    },
                    selector: (_, LoginViewModel model) {
                      return model.getCodeStatus;
                    },
                    builder: (context, int value, child) {
                      print("获取验证码btn build");
                      return Container(
                        alignment: Alignment.center,
                        decoration: BoxDecoration(
                          color: value == 1
                              ? Color(CommonColor.C_fff1f1)
                              : Color(CommonColor.C_f8f8f8),
                          borderRadius: BorderRadius.all(Radius.circular(15)),
                        ),
                        constraints:
                            BoxConstraints.expand(width: 102, height: 28),
                        child: Text(
                          "获取验证码",
                          style: TextStyle(
                            fontSize: 14,
                            height: 1,
                            color: value == 1
                                ? Color(CommonColor.C_MAIN_COLOR)
                                : Color(CommonColor.C_CCCCCC),
                            fontStyle: FontStyle.normal,
                            fontWeight: FontWeight.normal,
                            decoration: TextDecoration.none,
                          ),
                          textAlign: TextAlign.center,
                        ),
                      );
                    },
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }

  //输入密码
  Widget _passwordInputLayout() {
    return Store.selector<LoginViewModel, int>(
      shouldRebuild: (previous, next) {
        return previous != next;
      },
      selector: (_, LoginViewModel model) {
        return model.tabIndex;
      },
      builder: (context, int value, child) {
        print("请输入密码layout build");
        return Visibility(
          visible: value == 1 ? true : false,
          child: Container(
            margin: EdgeInsets.only(top: 12),
            constraints:
                BoxConstraints.expand(width: double.infinity, height: 50),
            child: Row(
              children: <Widget>[
                Expanded(
                  child: TextField(
                    textAlign: TextAlign.start,
                    textAlignVertical: TextAlignVertical.center,
                    controller: _pwdController,
                    keyboardType: TextInputType.text,
                    style: TextStyle(
                      fontSize: 16,
                      textBaseline: TextBaseline.alphabetic,
                    ),
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.zero,
                      hintText: "请输入密码",
                    ),
                    onChanged: (text) {
                      print("pwd onChanged:$text");
                      _checkEnableLogin(Store.read<LoginViewModel>(context));
                    },
                  ),
                ),
                Container(
                  margin: EdgeInsets.symmetric(horizontal: 12),
                  constraints: BoxConstraints.expand(
                      width: 1 / DisplayUtil.pixelRatio, height: 12),
                  alignment: Alignment.center,
                  decoration: BoxDecoration(
                    color: Color(CommonColor.C_DIVIDE),
                  ),
                ),
                GestureDetector(
                  onTap: () {
                    LoginViewModel model = Store.read<LoginViewModel>(context);
                    model.forgetPwdStatus = 2;
                  },
                  child: Store.selector<LoginViewModel, int>(
                    shouldRebuild: (previous, next) {
                      return previous != next;
                    },
                    selector: (_, LoginViewModel model) {
                      return model.forgetPwdStatus;
                    },
                    builder: (context, int value, child) {
                      print("忘记密码btn build");
                      return Container(
                        alignment: Alignment.center,
                        child: Text(
                          "忘记密码",
                          style: TextStyle(
                            fontSize: 14,
                            height: 1,
                            color: value == 1
                                ? CommonColor.COMMON_MAIN_COLOR
                                : Color(CommonColor.C_CCCCCC),
                            fontStyle: FontStyle.normal,
                            fontWeight: FontWeight.normal,
                            decoration: TextDecoration.none,
                          ),
                          textAlign: TextAlign.center,
                        ),
                      );
                    },
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }

  //登录按钮
  Widget _loginWidget() {
    return GestureDetector(
      onTap: () {},
      child: Store.selector<LoginViewModel, bool>(
        shouldRebuild: (previous, next) {
          return previous != next;
        },
        selector: (_, LoginViewModel model) {
          return model.canLogin;
        },
        builder: (context, bool value, child) {
          print("登录按钮btn build");
          return Container(
            margin: EdgeInsets.only(left: 0, top: 30, right: 0, bottom: 0),
            alignment: Alignment.center,
            constraints:
                BoxConstraints.expand(width: double.infinity, height: 60),
            decoration: BoxDecoration(
                gradient: value
                    ? ColorUtil.getGradient([
                        Color(CommonColor.C_MAIN_COLOR),
                        Color(CommonColor.C_FF5442)
                      ])
                    : ColorUtil.getGradient([
                        Color(CommonColor.C_FF869C),
                        Color(CommonColor.C_FF869C)
                      ]),
                borderRadius: BorderRadius.all(Radius.circular(30.0)),
                boxShadow: value
                    ? [
                        BoxShadow(
                            color: Color(0xffff0000),
                            blurRadius: 4.0,
                            offset: Offset(0, 2)),
                      ]
                    : []),
            child: Text(
              "登录",
              style: TextStyle(
                fontSize: 17,
                color: Colors.white,
                fontStyle: FontStyle.normal,
                fontWeight: FontWeight.normal,
                decoration: TextDecoration.none,
              ),
              textAlign: TextAlign.center,
            ),
          );
        },
      ),
    );
  }

  Widget _privacyPolicy() {
    return Container(
      margin: EdgeInsets.only(left: 0, top: 12, right: 20, bottom: 0),
      child: Text(
        "注册即代表同意《用户协议》和《隐私政策》",
        style: TextStyle(
          fontSize: 12,
          color: Color(CommonColor.C_666666),
          fontStyle: FontStyle.normal,
          fontWeight: FontWeight.normal,
          decoration: TextDecoration.none,
        ),
        textAlign: TextAlign.left,
      ),
    );
  }

  @override
  Widget buildChild(BuildContext context) {
    return Stack(
      fit: StackFit.loose,
      alignment: Alignment.bottomCenter,
      children: <Widget>[
        //轮播背景
        _banner(),

        //关闭按钮
        Positioned(
          top: 0,
          right: 0,
          child: Container(
            constraints: BoxConstraints.expand(width: 52, height: 52),
            margin: EdgeInsets.only(right: 15, top: 40),
            alignment: Alignment.centerRight,
            child: Image(
              image: AssetImage("assets/images/mv_ic_share_qq.png"),
            ),
          ),
        ),

        //输入区域,默认按Stack中的alignment居底部中间
        Container(
          margin: EdgeInsets.only(bottom: 50),
          padding: EdgeInsets.symmetric(horizontal: 20),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              //输入区域
              Container(
                padding: EdgeInsets.symmetric(horizontal: 20),
                alignment: Alignment.center,
                decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.all(Radius.circular(10.0))),
                child: Column(
                  children: <Widget>[
                    //标题
                    _loginMethodSwitch(),
                    //输入手机号
                    _accountInput(),

                    Stack(
                      children: <Widget>[
                        //输入短信验证码
                        _codeInputLayout(),
                        //输入密码
                        _passwordInputLayout(),
                      ],
                    ),
                    //登录按钮
                    _loginWidget(),
                    //登录方式
                    Container(
                      margin: EdgeInsets.only(top: 18),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: <Widget>[
                          Container(
                            margin: EdgeInsets.symmetric(horizontal: 12),
                            constraints: BoxConstraints.expand(
                                width: 12, height: 1 / DisplayUtil.pixelRatio),
                            alignment: Alignment.center,
                            decoration: BoxDecoration(
                              color: Color(CommonColor.C_CCCCCC),
                            ),
                          ),
                          Text(
                            "其他登录方式",
                            style: TextStyle(
                              fontSize: 11,
                              color: Color(CommonColor.C_999999),
                              fontStyle: FontStyle.normal,
                              fontWeight: FontWeight.normal,
                              decoration: TextDecoration.none,
                            ),
                            textAlign: TextAlign.center,
                          ),
                          Container(
                            margin: EdgeInsets.symmetric(horizontal: 12),
                            constraints: BoxConstraints.expand(
                                width: 12, height: 1 / DisplayUtil.pixelRatio),
                            alignment: Alignment.center,
                            decoration: BoxDecoration(
                              color: Color(CommonColor.C_CCCCCC),
                            ),
                          ),
                        ],
                      ),
                    ),

                    Container(
                      constraints: BoxConstraints.expand(width: 40, height: 40),
                      margin: EdgeInsets.only(top: 12, bottom: 12),
                      alignment: Alignment.center,
                      child: Image(
                        fit: BoxFit.cover,
                        image:
                            AssetImage("assets/images/mv_ic_share_wechat.png"),
                      ),
                    ),
                  ],
                ),
              ),
              //隐私政策
              _privacyPolicy(),
            ],
          ),
        ),
      ],
    );
  }

  @override
  Map<String, String> getParams1(int taskId) {
    return null;
  }

  @override
  void onDataError(int tag, Exception exception, int taskId) {}

  @override
  void onDataSuccess1(List<Task> result, int taskId) {}
}
