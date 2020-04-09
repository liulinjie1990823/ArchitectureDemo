import 'package:flutter/material.dart';
import 'package:flutter_base/utils/status_bar_util.dart';
import 'package:flutter_inv/inv/pages/page_template/template.dart';
import 'package:flutter_middle/utils/color_util.dart';
import 'package:flutter_middle/widgets/custom_sliver.dart';
import 'package:flutter_swiper/flutter_swiper.dart';

class InvHomePage extends StatefulWidget {
  final String title;

  InvHomePage({Key key, this.title}) : super(key: key);

  @override
  _InvHomePageState createState() => _InvHomePageState();
}

class _InvHomePageState extends State<InvHomePage>
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
      decoration: BoxDecoration(gradient: ColorUtil.getInvGradient()),
      child: Stack(
        children: <Widget>[
          Positioned(
              left: 15,
              child: Text(
                "返回",
                style: TextStyle(fontSize: 15, color: Colors.white),
              )),
          Text(
            "结婚请柬",
            style: TextStyle(fontSize: 17, color: Colors.white),
          ),
          Positioned(
              right: 15,
              child: Text(
                "婚礼MV",
                style: TextStyle(fontSize: 15, color: Colors.white),
              )),
        ],
        alignment: Alignment.center,
        fit: StackFit.loose,
      ),
    );
  }

  Widget _banner() {
    return Column(children: <Widget>[
      Container(
        height: 170,
        child: Swiper(
          itemBuilder: (BuildContext context, int index) {
            return Container(
              padding: EdgeInsets.fromLTRB(15, 0, 15, 15),
              child: Container(
                decoration: BoxDecoration(
//                  boxShadow: [
//                    BoxShadow(
//                      blurRadius: 0, //阴影范围
//                      spreadRadius: 0, //阴影浓度
//                      offset: Offset(0, 0),
//                      color: Colors.white, //阴影颜色
//                    ),
//                  ],
                  borderRadius: BorderRadius.circular(20), // 圆角也可控件一边圆角大小
                ),
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(20), // 圆角也可控件一边圆角大小
                  child: Image(
                    fit: BoxFit.fitWidth,
                    alignment: Alignment.topCenter, //可以控制Image中图片的位置
                    image: AssetImage("assets/images/login_guide_img_one.png"),
                  ),
                ),
              ),
            );
          },
          itemCount: 4,
          pagination: SwiperPagination(),
          autoplay: false,
          control: SwiperControl(
            iconPrevious: null,
            iconNext: null,
          ),
        ),
      ),
    ]);
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
          SliverToBoxAdapter(
            child: _banner(),
          ),
          SliverPersistentHeader(
            pinned: true,
            floating: false,
            delegate: StickyTabBarDelegate(
              child: TabBar(
                labelColor: Colors.black,
                controller: this.tabController,
                isScrollable: true,
                tabs: <Widget>[
                  Tab(text: 'Home'),
                  Tab(text: 'Profile'),
                ],
              ),
            ),
          ),
        ];
      },
      body: TabBarView(
        controller: this.tabController,
        children: <Widget>[
          ReTemplateList(),
          Center(child: Text('Content of Profile')),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    StatusBarUtil.statusBarTransparent(true);
    return Scaffold(
      body: DecoratedBox(
        decoration: BoxDecoration(gradient: ColorUtil.getInvGradient()),
        child: SafeArea(
          child: _content(),
        ),
      ),
    );
  }
}
