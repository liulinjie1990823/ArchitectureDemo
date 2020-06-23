import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_boost/container/boost_container.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_inv/inv/api/api_manager.dart';
import 'package:flutter_inv/inv/pages/page_inv/vo/inv_list_vo.dart';
import 'package:flutter_inv/main.dart';
import 'package:flutter_middle/api/api_manager.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/utils/display_util.dart';
import 'package:flutter_middle/store/store.dart';
import 'package:flutter_middle/widgets/common_widget.dart';
import 'package:flutter_easyrefresh/easy_refresh.dart';
import 'package:flutter_middle/widgets/custom_sliver.dart';

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
}

class MyInvListPage extends StatelessWidget {
  final Map<String, dynamic> params;

  MyInvListPage({this.params});

  InvitationViewModel _model;

  @override
  Widget build(BuildContext context) {
    print("InvitationViewModel");
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
            Padding(
              padding: const EdgeInsets.only(top: 16),
              child: Stack(
                alignment: Alignment.center,
                children: <Widget>[
                  Container(
                    margin: EdgeInsets.all(4),
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
                      String text = value.toString();
                      if (value > 99) {
                        text = "99+";
                      }
                      return Positioned(
                        top: 0,
                        right: 0,
                        child: Container(
                          padding: EdgeInsets.all(1),
                          alignment: Alignment.center,
                          constraints: BoxConstraints(
                            minWidth: 18,
                            minHeight: 18,
                          ),
                          decoration: BoxDecoration(
                              color: CommonColor.COMMON_WHITE,
                              shape: BoxShape.rectangle,
                              borderRadius:
                                  BorderRadius.all(Radius.circular(9.0)),
                              border: Border.all(
                                color: CommonColor.COMMON_MAIN_COLOR,
                                width: 1,
                                style: BorderStyle.solid,
                              )),
                          child: Text(text,
                              style: TextStyle(
                                fontSize: 12,
                                color: CommonColor.COMMON_MAIN_COLOR,
                              )),
                        ),
                      );
                    },
                  )
                ],
              ),
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
    return Container(
      color: CommonColor.COMMON_MAIN_COLOR,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.start,
        children: <Widget>[
          _sliverHeaderOne("images/mv_ic_invitation_list_guests.png", "宾客", 0),
          _sliverHeaderOne(
              "images/mv_ic_invitation_list_blessing.png", "礼物·祝福", 1),
          _sliverHeaderOne(
              "images/mv_ic_invitation_list_template_card.png", "模板卡", 2),
          _sliverHeaderOne("images/mv_ic_invitation_list_mv.png", "婚礼MV", 3),
        ],
      ),
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
            return Divider(
              height: 1,
            );
          },
          itemCount: invListVo.data.invitationList.list.length),
    );
  }

  Widget _listView3(BuildContext context) {
    InvListVo invListVo = Store.read<InvitationViewModel>(context).invListVo;
    return SliverList(
      delegate: SliverChildBuilderDelegate(
        (context, index) {
          return _listViewItem(invListVo.data.invitationList.list[index]);
        },
        childCount: invListVo.data.invitationList.list.length,
      ),
    );
  }

  Widget _listViewItem(ListVo item) {
    double _imageWidth = 125 * DisplayUtil.width / 375.0;

    print(item.cover);
    return IntrinsicHeight(
      child: Column(
        children: <Widget>[
          Container(
            padding: EdgeInsets.only(left: 16, top: 16, right: 16, bottom: 8),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Container(
                  padding: EdgeInsets.only(right: 20),
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.baseline,
                    textBaseline: TextBaseline.alphabetic,
                    children: <Widget>[
                      Text("09",
                          style: TextStyle(
                              fontSize: 28,
                              color: Color(CommonColor.C_333333))),
                      Text("6月",
                          style: TextStyle(
                              fontSize: 12,
                              color: Color(CommonColor.C_333333))),
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
                            child: Builder(
                              builder: (context) {
                                return Container(
                                  height: 30,
                                  child: FlatButton(
                                    onPressed: () {
                                      _showModalBottomSheet(context);
                                    },
                                    child: Image(
                                      width: 20,
                                      height: 20,
                                      fit: BoxFit.scaleDown,
                                      image: AssetImage(
                                          "images/mv_ic_template_list_setting.png",
                                          package: MyApp.FLUTTER_INV),
                                    ),
                                  ),
                                );
                              },
                            ),
                          ),
                          Expanded(
                            child: Builder(
                              builder: (context) {
                                return Container(
                                  height: 30,
                                  child: FlatButton(
                                    onPressed: () {
                                      _showPersistentBottomSheet(context);
                                    },
                                    child: Image(
                                      width: 20,
                                      height: 20,
                                      fit: BoxFit.scaleDown,
                                      image: AssetImage(
                                          "images/mv_ic_template_list_guests.png",
                                          package: MyApp.FLUTTER_INV),
                                    ),
                                  ),
                                );
                              },
                            ),
                          ),
                          Expanded(
                            child: Builder(
                              builder: (context) {
                                return Container(
                                  height: 30,
                                  child: FlatButton(
                                    onPressed: () {},
                                    child: Image(
                                      width: 20,
                                      height: 20,
                                      fit: BoxFit.scaleDown,
                                      image: AssetImage(
                                          "images/mv_ic_template_list_gift.png",
                                          package: MyApp.FLUTTER_INV),
                                    ),
                                  ),
                                );
                              },
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
          Divider(
            height: 1,
          ),
        ],
      ),
    );
  }

  _showModalBottomSheet(BuildContext context) {
    showModalBottomSheet(
        context: context,
        backgroundColor: Colors.transparent,
        builder: (context) {
          return Container(
              decoration: BoxDecoration(
                  color: Colors.greenAccent,
                  borderRadius:
                      BorderRadius.vertical(top: Radius.circular(16))),
              height: 300,
              child: Center(
                child: Text('ModalBottomSheet'),
              ));
        });
  }

  _showPersistentBottomSheet(BuildContext context) {
    showBottomSheet(
        context: context,
        backgroundColor: Colors.transparent,
        builder: (context) {
          return Container(
              decoration: BoxDecoration(
                  color: Colors.greenAccent,
                  borderRadius:
                      BorderRadius.vertical(top: Radius.circular(16))),
              height: 300,
              child: Center(
                child: Text('ModalBottomSheet'),
              ));
        });
  }

  Widget _content(BuildContext context) {
//    return Column(
//      children: <Widget>[
////        _sliverHeaderBar(),
//        Expanded(
//          child: NestedScrollView(
//            headerSliverBuilder: (context, bool) {
//              return [
//                SliverToBoxAdapter(
//                  child: _sliverHeaderBar(),
//                ),
//              ];
//            },
//            body: _listView(context),
//          ),
//        ),
//      ],
//    );
    return Column(
      children: <Widget>[
        Expanded(
          child: Container(
            color: CommonColor.COMMON_WHITE,
            child: EasyRefresh.builder(
              builder: (context, physics, header, footer) {
                return CustomScrollView(
                  physics: physics,
                  slivers: <Widget>[
                    SliverPersistentHeader(
                      pinned: false,
                      delegate: StickyTitleBarDelegate(
                        height: 110,
                        child: _sliverHeaderBar(),
                      ),
                    ),
                    header,
                    _listView3(context),
                    footer,
                  ],
                );
              },
              onRefresh: () async {
                getInvitationListFuture().then((value) {
                  InvitationViewModel model =
                      Store.read<InvitationViewModel>(context);
                  model.invListVoAndNotify = value;
                });
              },
              onLoad: () async {},
            ),
          ),
        ),
      ],
    );
    return Column(
      children: <Widget>[
//        _sliverHeaderBar(),
        Expanded(
          child: Container(
            color: CommonColor.COMMON_WHITE,
            child: EasyRefresh.custom(
              slivers: <Widget>[
                SliverToBoxAdapter(
                  child: _sliverHeaderBar(),
                ),
                _listView3(context),
              ],
              onRefresh: () async {
                getInvitationListFuture().then((value) {
                  InvitationViewModel model =
                      Store.read<InvitationViewModel>(context);
                  model.invListVoAndNotify = value;
                });
              },
              onLoad: () async {},
            ),
          ),
        ),
      ],
    );
  }

  Future<InvListVo> getInvitationListFuture() {
    final client = InvRestClient(ApiManager.dio());
    return client.getInvitations();
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
    final BoostContainerSettings settings = BoostContainer.of(context).settings;
    FlutterBoost.singleton.close(settings.uniqueId,
        result: <String, dynamic>{'result': 'data from inv'});
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

  @override
  String leftWidgetText(BuildContext context) {
    return "返回";
  }
}
