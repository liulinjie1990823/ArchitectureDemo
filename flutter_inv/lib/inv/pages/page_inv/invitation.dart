import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_inv/inv/api/api_manager.dart';
import 'package:flutter_inv/inv/pages/page_inv/vo/inv_list_vo.dart';
import 'package:flutter_inv/main.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/utils/display_util.dart';
import 'package:flutter_middle/store/store.dart';
import 'package:flutter_middle/widgets/common_widget.dart';

import 'package:dio/dio.dart';
import 'package:dio/adapter.dart';
import 'package:logger/logger.dart';

class InvitationViewModel extends ChangeNotifier {
  InvListVo _invListVo;

  InvListVo get invListVo => _invListVo;

  set invListVo(InvListVo value) {
    _invListVo = value;
  }

  set invListVoAndNotify(InvListVo value) {
    _invListVo = value;
    notifyListeners();
  }

  void getInvitationList() async {
    Dio dio=Dio();
    (dio.httpClientAdapter as DefaultHttpClientAdapter).onHttpClientCreate = (client) {
      // config the http client
      client.findProxy = (uri) {
        //proxy all request to localhost:8888
        return "PROXY 192.168.31.58:8888";
      };
      // you can also create a HttpClient to dio
      // return HttpClient();
    };
    (dio.httpClientAdapter as DefaultHttpClientAdapter).onHttpClientCreate  = (client) {
      client.badCertificateCallback=(X509Certificate cert, String host, int port){
        return true;
      };
    };
    final client = RestClient(dio);
    client.getTasks().then((value) {
      if (value != null && value.code == 0) {
        if (value.data != null &&
            value.data.invitationList != null &&
            value.data.invitationList.list != null &&
            value.data.invitationList.list.length != 0) {
          invListVo = value;
        } else {
          print("invListVo empty");
        }
      }
    });
  }

  void loadData() {
    print("loadData");
    getInvitationList();
  }
}

class MyInvListPage extends StatelessWidget {
  InvitationViewModel _model;

  @override
  Widget build(BuildContext context) {
    print("MyInvListPage build");
    _model = new InvitationViewModel();
    return Store.init(_model, child: _MyInvListPage());
  }
}

class _MyInvListPage extends CommonTitleBarPage {
  Color mainColor() {
    return CommonColor.COMMON_MAIN_COLOR;
  }

  bool statusBarTextWhite() {
    return true;
  }

  Color titleBarTextColor() {
    return Colors.white;
  }

  Widget _sliverHeaderOne(String image, String text, int index) {
    return Flexible(
      flex: 1,
      fit: FlexFit.tight,
      child: FlatButton(
        onPressed: () {},
        child: Column(
          children: <Widget>[
            Stack(
              alignment: Alignment.center,
              children: <Widget>[
                Container(
                  margin: EdgeInsets.all(5),
                  child: Image(
                    width: 40,
                    height: 40,
                    fit: BoxFit.scaleDown,
                    image: AssetImage(image, package: MyApp.FLUTTER_INV),
                  ),
                ),
                Store.selector<InvitationViewModel, int>(
                  shouldRebuild: (previous, next) {
                    return previous != next;
                  },
                  selector: (_, InvitationViewModel model) {
                    if (index == 0) {
                      return model.invListVo.data.gusetCount;
                    } else if (index == 1) {
                      return model.invListVo.data.giftCount;
                    } else {
                      return model.invListVo.data.wishCount;
                    }
                  },
                  builder: (context, int value, child) {
                    print("build" + index.toString());
                    if (value == 0) {
                      return Text("");
                    }
                    return Positioned(
                      top: 0,
                      right: 0,
                      child: Container(
                        width: 20,
                        height: 20,
                        decoration: BoxDecoration(
                            color: CommonColor.COMMON_WHITE,
                            shape: BoxShape.circle,
                            border: Border.all(
                              color: CommonColor.COMMON_MAIN_COLOR,
                              width: 1,
                              style: BorderStyle.solid,
                            )),
                        child: Center(
                          child: Text(value.toString(),
                              style: TextStyle(
                                color: CommonColor.COMMON_MAIN_COLOR,
                              )),
                        ),
                      ),
                    );
                  },
                )
              ],
            ),
            Padding(
              padding: EdgeInsets.only(top: 6.0, bottom: 16.0),
              child: Text(
                text,
                style: TextStyle(
                    color: CommonColor.COMMON_WHITE,
                    fontSize: 14,
                    fontWeight: FontWeight.w800),
              ),
            )
          ],
        ),
      ),
    );
  }

  Widget _sliverHeaderBar() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.start,
      children: <Widget>[
        _sliverHeaderOne("images/mv_ic_invitation_list_guests.png", "宾客", 0),
        _sliverHeaderOne(
            "images/mv_ic_invitation_list_blessing.png", "礼物·祝福", 1),
        _sliverHeaderOne(
            "images/mv_ic_invitation_list_template_card.png", "模板卡", 2),
        _sliverHeaderOne("images/mv_ic_invitation_list_mv.png", "婚礼MV", 3),
      ],
    );
  }

  Widget _gridView() {
    return Container(
      color: Colors.white,
      child: GridView.builder(
        gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 3,
          childAspectRatio: 1,
          crossAxisSpacing: 10,
          mainAxisSpacing: 20,
        ),
        itemCount: 20,
        itemBuilder: (context, index) {
          return Container(
            color: Colors.black,
            child: Image(
              fit: BoxFit.scaleDown,
              image: NetworkImage("_imageUrl"),
            ),
          );
        },
      ),
    );
  }

  Widget _listView2(BuildContext context) {
    String _imageUrl =
        "https://img.hbhcdn.com/dmp/s/merchant/1583251200/jh-img-orig-ga_1235068280189886464_1563_1172_1802512.jpg";
    return Container(
      color: Colors.white,
      child: ListView.builder(
        itemCount: 20,
        itemBuilder: (context, index) {
          print("itemBuilder" + index.toString());
          return Container(
            color: Colors.black,
            child: Image(
              fit: BoxFit.scaleDown,
              image: NetworkImage(_imageUrl),
            ),
          );
        },
      ),
    );
  }

  Widget _listView(BuildContext context) {
    InvListVo invListVo = Store.read<InvitationViewModel>(context).invListVo;
    return Container(
      color: Colors.white,
      child: ListView.separated(
          itemBuilder: (BuildContext context, int index) {
            print("itemBuilder" + index.toString());
            return _listViewItem(invListVo.data.invitationList.list[index]);
          },
          separatorBuilder: (BuildContext context, int index) {
            return Divider();
          },
          itemCount: invListVo.data.invitationList.list.length),
    );
  }

  String _imageUrl =
      "https://img.hbhcdn.com/dmp/s/merchant/1583251200/jh-img-orig-ga_1235068280189886464_1563_1172_1802512.jpg";

  Widget _listViewItem(ListVo item) {
    double _imageWidth = 125 * DisplayUtil.width / 375.0;

    print(item.cover);
    return IntrinsicHeight(
      child: Container(
        padding: EdgeInsets.symmetric(horizontal: 20),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Container(
              padding: EdgeInsets.only(right: 20),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.baseline,
                textBaseline: TextBaseline.alphabetic,
                children: <Widget>[
                  Text(
                    "09",
                    style: TextStyle(
                        fontSize: 28, color: Color(CommonColor.C_333333)),
                  ),
                  Text(
                    "6月",
                    style: TextStyle(
                        fontSize: 12, color: Color(CommonColor.C_333333)),
                  ),
                ],
              ),
            ),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Container(
                    width: _imageWidth,
                    height: _imageWidth *
                        item.coverShowHeight /
                        (item.coverShowWidth.toDouble()),
                    child: Image(
                      fit: BoxFit.cover,
                      image: NetworkImage(item.cover),
                    ),
                  ),
                  Row(
                    children: <Widget>[
                      Container(
                          width: _imageWidth,
                          child: Text(
                            item.coverShowWidth.toString(),
                            style: TextStyle(
                                fontSize: 12,
                                color: Color(CommonColor.C_999999)),
                          )),
                      Expanded(
                        child: Image(
                          width: 20,
                          height: 20,
                          fit: BoxFit.scaleDown,
                          image: AssetImage(
                              "images/mv_ic_template_list_setting.png",
                              package: MyApp.FLUTTER_INV),
                        ),
                      ),
                      Expanded(
                        child: Image(
                          width: 20,
                          height: 20,
                          fit: BoxFit.scaleDown,
                          image: AssetImage(
                              "images/mv_ic_template_list_guests.png",
                              package: MyApp.FLUTTER_INV),
                        ),
                      ),
                      Expanded(
                        child: Image(
                          width: 20,
                          height: 20,
                          fit: BoxFit.scaleDown,
                          image: AssetImage(
                              "images/mv_ic_template_list_gift.png",
                              package: MyApp.FLUTTER_INV),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _content(BuildContext context) {
    return NestedScrollView(
      headerSliverBuilder: (context, bool) {
        return [
          SliverToBoxAdapter(
            child: _sliverHeaderBar(),
          ),
        ];
      },
      body: _listView(context),
    );
  }

  final logger = Logger();

  Future<InvListVo> getInvitationListFuture() {
    Dio dio=Dio();
    (dio.httpClientAdapter as DefaultHttpClientAdapter).onHttpClientCreate = (client) {
      // config the http client
      client.findProxy = (uri) {
        //proxy all request to localhost:8888
        return "PROXY 192.168.31.58:8888";
      };
      // you can also create a HttpClient to dio
      // return HttpClient();
    };
    (dio.httpClientAdapter as DefaultHttpClientAdapter).onHttpClientCreate  = (client) {
      client.badCertificateCallback=(X509Certificate cert, String host, int port){
        return true;
      };
    };
    final client = RestClient(dio);
    return client.getTasks();
  }

  @override
  Widget buildChild(BuildContext context) {
    InvitationViewModel model = Store.read<InvitationViewModel>(context);
    return FutureBuilder<InvListVo>(
      future: getInvitationListFuture(),
      builder: (BuildContext context, AsyncSnapshot<InvListVo> snapshot) {
        return CommonStatusBuilder<InvListVo>(
          snapshot: snapshot,
          builder: (BuildContext context, AsyncSnapshot<InvListVo> snapshot) {
            model.invListVo = snapshot.data;
            return Store.selector<InvitationViewModel, InvitationList>(
              shouldRebuild: (previous, next) {
                return previous != next;
              },
              selector: (BuildContext context, InvitationViewModel model) {
                return model.invListVo.data.invitationList;
              },
              builder: (context, value, child) {
                print("_content build");
                return _content(context);
              },
            );
          },
        );
      },
    );
  }

  @override
  void onLeftTap(BuildContext context) {
    InvitationViewModel model = Store.read<InvitationViewModel>(context);
    model.invListVo.data.gusetCount++;
    model.notifyListeners();
  }

  @override
  void onRightTap(BuildContext context) {
    InvitationViewModel model = Store.read<InvitationViewModel>(context);
    model.invListVo.data.giftCount++;
    model.notifyListeners();
  }

  @override
  String centerWidgetText(BuildContext context) {
    return "我的请柬";
  }
}
