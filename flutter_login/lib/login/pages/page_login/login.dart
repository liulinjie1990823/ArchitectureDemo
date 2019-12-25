import 'package:flutter/material.dart';
import 'package:flutter_login/login/pages/page_login/counter_model.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/utils/color_util.dart';
import 'package:flutter_middle/utils/display_util.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
import 'package:scoped_model/scoped_model.dart';

class Login extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Login Demo',
      theme: ThemeData(
        //TextFiled边框
        primaryColor: Color(CommonColor.C_MAIN_COLOR),
        //TextFiled边框
        primarySwatch: Colors.blue,
        //
        accentColor: Colors.green,

        //光标颜色
        cursorColor: Color(CommonColor.C_MAIN_COLOR),
        //
        textTheme: TextTheme(
            subhead: TextStyle(textBaseline: TextBaseline.alphabetic)),
        //输入设置
        inputDecorationTheme: InputDecorationTheme(
//          fillColor: Colors.cyan,
//          filled: true,
          hintStyle: TextStyle(
            fontSize: 16,
            textBaseline: TextBaseline.alphabetic,
            color: Color(CommonColor.C_HINT_TEXT),
          ),
          labelStyle: TextStyle(
            fontSize: 16,
            textBaseline: TextBaseline.alphabetic,
            color: Color(CommonColor.C_HINT_TEXT),
          ),
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(
              style: BorderStyle.none,
              color: Color(CommonColor.C_EEEEEE),
              width: 1,
            ),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(
              style: BorderStyle.none,
              color: Color(CommonColor.C_MAIN_COLOR),
              width: 1,
            ),
          ),
        ),
      ),
      home: Scaffold(resizeToAvoidBottomInset: false, body: LoginPage()),
    );
  }
}

class LoginPage extends StatelessWidget {
//  final UserRepository userRepository;
//
//  LoginPage({Key key, @required this.userRepository})
//      : assert(userRepository != null),
//        super(key: key);

  TextEditingController _accountController = new TextEditingController();
  TextEditingController _pwdController = new TextEditingController();
  TextEditingController _codeController = new TextEditingController();

  @override
  Widget build(BuildContext context) {
    TextFormField _account = TextFormField(
      controller: _accountController,
      decoration: InputDecoration(labelText: "", hintText: "请输入手机号"),
    );

    TextFormField _pwd = TextFormField(
      controller: _pwdController,
      decoration: InputDecoration(labelText: "", hintText: "请输入密码"),
    );

    TextFormField _code = TextFormField(
      controller: _codeController,
      decoration: InputDecoration(labelText: "", hintText: "请输入短信验证码"),
    );
    CounterModel counterModel = CounterModel();

    return ScopedModel<CounterModel>(
      model: counterModel,
      child: Stack(
        fit: StackFit.loose,
        alignment: Alignment.bottomCenter,
        children: <Widget>[
          //page
          Swiper(
            itemBuilder: (BuildContext context, int index) {
              return Image(
                fit: BoxFit.cover,
                image: AssetImage("assets/images/login_guide_img_one.png"),
              );
            },
            itemCount: 4,
            pagination: SwiperPagination(),
            control: SwiperControl(),
          ),

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

          //
          Container(
            margin: EdgeInsets.only(bottom: 50),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                //输入区域
                Container(
                  margin:
                      EdgeInsets.only(left: 20, top: 0, right: 20, bottom: 10),
                  padding: EdgeInsets.symmetric(horizontal: 20),
                  alignment: Alignment.center,
                  decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.all(Radius.circular(10.0))),
                  child: Column(
                    children: <Widget>[
                      //标题
                      Container(
                        padding:
                            EdgeInsets.symmetric(vertical: 20, horizontal: 0),
                        child: Row(
                          children: <Widget>[
                            Expanded(
                              flex: 1,
                              child: GestureDetector(
                                onTap: () {
                                  counterModel.index = 0;
                                },
                                child: ScopedModelDescendant<CounterModel>(
                                    builder: (context, child, model) {
                                  return Text(
                                    "验证码登录",
                                    style: TextStyle(
                                      fontSize: 19,
                                      color: counterModel.index == 0
                                          ? Color(CommonColor.C_NORMAL_TEXT)
                                          : Color(CommonColor.C_UN_ENABLE_TEXT),
                                      fontStyle: FontStyle.normal,
                                      fontWeight: FontWeight.w600,
                                      decoration: TextDecoration.none,
                                    ),
                                    textAlign: TextAlign.center,
                                  );
                                }),
                              ),
                            ),
                            Container(
                              constraints: BoxConstraints.expand(
                                  width: 1 / DisplayUtil.pixelRatio,
                                  height: 16),
                              alignment: Alignment.center,
                              decoration: BoxDecoration(
                                color: Color(CommonColor.C_DIVIDE),
                              ),
                            ),
                            Expanded(
                              flex: 1,
                              child: GestureDetector(
                                onTap: () {
                                  counterModel.index = 1;
                                },
                                child: ScopedModelDescendant<CounterModel>(
                                  builder: (context, child, model) {
                                    return Text(
                                      "密码登录",
                                      style: TextStyle(
                                        fontSize: 19,
                                        color: counterModel.index == 1
                                            ? Color(CommonColor.C_NORMAL_TEXT)
                                            : Color(
                                                CommonColor.C_UN_ENABLE_TEXT),
                                        fontStyle: FontStyle.normal,
                                        fontWeight: FontWeight.w600,
                                        decoration: TextDecoration.none,
                                      ),
                                      textAlign: TextAlign.center,
                                    );
                                  },
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),

                      //手机号
                      Container(
                        constraints: BoxConstraints.expand(
                            width: double.infinity, height: 50),
                        child: TextField(
                          textAlign: TextAlign.start,
                          textAlignVertical: TextAlignVertical.center,
                          controller: _accountController,
                          keyboardType: TextInputType.number,
                          //输入style
                          style: TextStyle(
                            fontSize: 16,
                            textBaseline: TextBaseline.alphabetic,
                          ),
                          //输入装饰
                          decoration: InputDecoration(
                            contentPadding: EdgeInsets.zero,
                            hintText: "请输入手机号",
                          ),
                        ),
                      ),

                      Stack(
                        children: <Widget>[
                          //短信验证码
                          ScopedModelDescendant<CounterModel>(
                              builder: (context, child, model) {
                            return Visibility(
                              visible: model.index == 0 ? true : false,
                              child: Container(
                                margin: EdgeInsets.only(top: 12),
                                constraints: BoxConstraints.expand(
                                    width: double.infinity, height: 50),
                                child: Row(
                                  children: <Widget>[
                                    Expanded(
                                      child: TextField(
                                        textAlign: TextAlign.start,
                                        textAlignVertical:
                                            TextAlignVertical.center,
                                        controller: _pwdController,
                                        keyboardType: TextInputType.text,
                                        style: TextStyle(
                                          fontSize: 16,
                                          textBaseline: TextBaseline.alphabetic,
                                        ),
                                        decoration: InputDecoration(
                                          contentPadding: EdgeInsets.zero,
                                          hintText: "请输入短信验证码",
                                        ),
                                      ),
                                    ),
                                    Container(
                                      alignment: Alignment.center,
                                      decoration: BoxDecoration(
                                        color: Color(0xfffff1f1),
                                        borderRadius: BorderRadius.all(
                                            Radius.circular(15)),
                                      ),
                                      constraints: BoxConstraints.expand(
                                          width: 102, height: 28),
                                      child: Text(
                                        "获取验证码",
                                        style: TextStyle(
                                          fontSize: 14,
                                          height: 1,
                                          color: Color(CommonColor.C_CCCCCC),
                                          fontStyle: FontStyle.normal,
                                          fontWeight: FontWeight.normal,
                                          decoration: TextDecoration.none,
                                        ),
                                        textAlign: TextAlign.center,
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            );
                          }),
                          //密码
                          ScopedModelDescendant<CounterModel>(
                              builder: (context, child, model) {
                            return Visibility(
                              visible: model.index == 1 ? true : false,
                              child: Container(
                                margin: EdgeInsets.only(top: 12),
                                constraints: BoxConstraints.expand(
                                    width: double.infinity, height: 50),
                                child: Row(
                                  children: <Widget>[
                                    Expanded(
                                      child: TextField(
                                        textAlign: TextAlign.start,
                                        textAlignVertical:
                                            TextAlignVertical.center,
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
                                      ),
                                    ),
                                    Container(
                                      margin:
                                          EdgeInsets.symmetric(horizontal: 12),
                                      constraints: BoxConstraints.expand(
                                          width: 1 / DisplayUtil.pixelRatio,
                                          height: 12),
                                      alignment: Alignment.center,
                                      decoration: BoxDecoration(
                                        color: Color(CommonColor.C_DIVIDE),
                                      ),
                                    ),
                                    Container(
                                      alignment: Alignment.center,
                                      child: Text(
                                        "忘记密码",
                                        style: TextStyle(
                                          fontSize: 14,
                                          height: 1,
                                          color: Color(CommonColor.C_CCCCCC),
                                          fontStyle: FontStyle.normal,
                                          fontWeight: FontWeight.normal,
                                          decoration: TextDecoration.none,
                                        ),
                                        textAlign: TextAlign.center,
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            );
                          }),
                        ],
                      ),

                      //登录
                      ScopedModelDescendant<CounterModel>(
                          builder: (context, child, model) {
                        return Container(
                          margin: EdgeInsets.only(
                              left: 0, top: 30, right: 0, bottom: 0),
                          alignment: Alignment.center,
                          constraints: BoxConstraints.expand(
                              width: double.infinity, height: 60),
                          decoration: BoxDecoration(
                              gradient: model.ok
                                  ? ColorUtil.getGradient([
                                      Color(CommonColor.C_MAIN_COLOR),
                                      Color(CommonColor.C_FF5442)
                                    ])
                                  : ColorUtil.getGradient([
                                      Color(CommonColor.C_EEEEEE),
                                      Color(CommonColor.C_CCCCCC)
                                    ]),
                              borderRadius:
                                  BorderRadius.all(Radius.circular(30.0))),
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
                      }),

                      //登录方式
                      Container(
                        margin: EdgeInsets.only(top: 18),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: <Widget>[
                            Container(
                              margin: EdgeInsets.symmetric(horizontal: 12),
                              constraints: BoxConstraints.expand(
                                  width: 12,
                                  height: 1 / DisplayUtil.pixelRatio),
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
                                  width: 12,
                                  height: 1 / DisplayUtil.pixelRatio),
                              alignment: Alignment.center,
                              decoration: BoxDecoration(
                                color: Color(CommonColor.C_CCCCCC),
                              ),
                            ),
                          ],
                        ),
                      ),

                      Container(
                        constraints:
                            BoxConstraints.expand(width: 40, height: 40),
                        margin: EdgeInsets.only(top: 12, bottom: 12),
                        alignment: Alignment.center,
                        child: Image(
                          fit: BoxFit.cover,
                          image: AssetImage(
                              "assets/images/mv_ic_share_wechat.png"),
                        ),
                      ),
                    ],
                  ),
                ),
                //隐私政策
                Container(
                  child: Text(
                    "注册即代表同意《用户协议》和《隐私政策》",
                    style: TextStyle(
                      fontSize: 14,
                      color: Colors.white,
                      fontStyle: FontStyle.normal,
                      fontWeight: FontWeight.normal,
                      decoration: TextDecoration.none,
                    ),
                    textAlign: TextAlign.left,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
