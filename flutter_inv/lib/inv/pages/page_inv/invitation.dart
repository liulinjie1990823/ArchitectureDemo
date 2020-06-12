import 'package:flutter/material.dart';
import 'package:flutter_base/utils/status_bar_util.dart';
import 'package:flutter_inv/main.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/utils/color_util.dart';
import 'package:flutter_middle/utils/display_util.dart';
import 'package:flutter_middle/widgets/custom_sliver.dart';

class ReTemplateList extends StatelessWidget {
  String _imageUrl =
      "https://img.hbhcdn.com/dmp/s/merchant/1583251200/jh-img-orig-ga_1235068280189886464_1563_1172_1802512.jpg";

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

  @override
  Widget build(BuildContext context) {
    return _gridView();
  }
}

class MyInvListPage extends StatefulWidget {
  final String title;

  MyInvListPage({Key key, this.title}) : super(key: key);

  @override
  _MyInvListPageState createState() => _MyInvListPageState();
}

class _MyInvListPageState extends State<MyInvListPage>
    with SingleTickerProviderStateMixin {
  TabController tabController;

  @override
  void initState() {
    super.initState();
    this.tabController = TabController(length: 2, vsync: this);
  }

  Widget _title({double height}) {
    return Container(
      constraints:
          BoxConstraints.expand(width: double.infinity, height: height),
      decoration: BoxDecoration(color: CommonColor.MAIN_COLOR()),
      child: Stack(
        children: <Widget>[
          Positioned(
              left: 15,
              child: Text(
                "返回",
                style: TextStyle(fontSize: 15, color: Colors.white),
              )),
          Text(
            "我的请柬",
            style: TextStyle(fontSize: 17, color: Colors.white),
          ),
          Positioned(
              right: 15,
              child: Text(
                "意见反馈",
                style: TextStyle(fontSize: 15, color: Colors.white),
              )),
        ],
        alignment: Alignment.center,
        fit: StackFit.loose,
      ),
    );
  }

  Widget _sliverHeaderOne(String image, String text) {
    return Flexible(
      flex: 1,
      fit: FlexFit.tight,
      child: Column(
        children: <Widget>[
          Image(
            width: 40,
            height: 40,
            fit: BoxFit.scaleDown,
            image: AssetImage(image, package: MyApp.FLUTTER_INV),
          ),
          Padding(
            padding: EdgeInsets.only(top: 6.0, bottom: 16.0),
            child: Text(
              text,
              style: TextStyle(color: CommonColor.WHITE(), fontSize: 14),
            ),
          )
        ],
      ),
    );
  }

  Widget _sliverHeaderBar() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.start,
      children: <Widget>[
        _sliverHeaderOne("images/mv_ic_invitation_list_guests.png", "宾客"),
        _sliverHeaderOne("images/mv_ic_invitation_list_blessing.png", "礼物·祝福"),
        _sliverHeaderOne(
            "images/mv_ic_invitation_list_template_card.png", "模板卡"),
        _sliverHeaderOne("images/mv_ic_invitation_list_mv.png", "婚礼MV"),
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

  Widget _listView() {
    return Container(
      color: Colors.white,
      child: ListView.separated(
          itemBuilder: (BuildContext context, int index) {
            return _listViewItem();
          },
          separatorBuilder: (BuildContext context, int index) {
            return Divider();
          },
          itemCount: 20),
    );
  }

  String _imageUrl =
      "https://img.hbhcdn.com/dmp/s/merchant/1583251200/jh-img-orig-ga_1235068280189886464_1563_1172_1802512.jpg";

  Widget _listViewItem() {
    double _imageWidth = 125 * DisplayUtil.width / 375.0;
    double _textHeight = 20;
    double _itemHeight = _imageWidth * 16 / 9.0 + _textHeight;
    return Container(
      height: _itemHeight,
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
          Container(
            width: _imageWidth,
            height: _itemHeight,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Expanded(
                  child: Image(
                    fit: BoxFit.cover,
                    image: NetworkImage(_imageUrl),
                  ),
                ),
                Container(
                    height: _textHeight,
                    child: Text(
                      "最后编辑：2020.06.09",
                      style: TextStyle(
                          fontSize: 12, color: Color(CommonColor.C_999999)),
                    ))
              ],
            ),
          ),
          Expanded(
            child: Align(
              alignment: Alignment.bottomCenter,
              child: Row(
                children: <Widget>[
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
                      image: AssetImage("images/mv_ic_template_list_guests.png",
                          package: MyApp.FLUTTER_INV),
                    ),
                  ),
                  Expanded(
                    child: Image(
                      width: 20,
                      height: 20,
                      fit: BoxFit.scaleDown,
                      image: AssetImage("images/mv_ic_template_list_gift.png",
                          package: MyApp.FLUTTER_INV),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _content() {
    return NestedScrollView(
      headerSliverBuilder: (context, bool) {
        return [
          SliverPersistentHeader(
            pinned: true,
            delegate: StickyTitleBarDelegate(
              height: 48,
              child: _title(height: 48),
            ),
          ),
//          SliverPersistentHeader(
//            pinned: true,
//            delegate: StickyTitleBarDelegate(
//              height: 150,
//              child: _sliverHeaderBar(),
//            ),
//          ),
          SliverToBoxAdapter(
            child: _sliverHeaderBar(),
          ),
        ];
      },
      body: _listView(),
    );
  }

  @override
  Widget build(BuildContext context) {
    StatusBarUtil.statusBarTransparent(true);
    return Scaffold(
      body: DecoratedBox(
        decoration: BoxDecoration(color: CommonColor.MAIN_COLOR()),
        child: SafeArea(
          child: _content(),
        ),
      ),
    );
  }
}

