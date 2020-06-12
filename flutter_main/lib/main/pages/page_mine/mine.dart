import 'package:flutter/material.dart';
import 'package:flutter_inv/main.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/utils/color_util.dart';

class MinePage extends StatelessWidget {
  Widget _sliverAppBar() {
    return SliverAppBar(
      stretch: true,
      floating: false,
      pinned: true,
      snap: false,
      onStretchTrigger: () {
        // Function callback for stretch
        return;
      },
      expandedHeight: 200.0,
      flexibleSpace: FlexibleSpaceBar(
//        stretchModes: <StretchMode>[
//          StretchMode.zoomBackground,
//          StretchMode.blurBackground,
//          StretchMode.fadeTitle,
//        ],
        centerTitle: false,
        title: const Text('Flight Report'),
        background: Stack(
          fit: StackFit.expand,
          children: [
            Image.network(
              'https://flutter.github.io/assets-for-api-docs/assets/widgets/owl-2.jpg',
              fit: BoxFit.cover,
            ),
            const DecoratedBox(
              decoration: BoxDecoration(
                gradient: LinearGradient(
                  begin: Alignment(0.0, 0.5),
                  end: Alignment(0.0, 0.0),
                  colors: <Color>[
                    Color(0x60000000),
                    Color(0x00000000),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _sliverList() {
    return SliverList(
      delegate: SliverChildBuilderDelegate(
        (BuildContext context, int index) {
          return Container(
            height: 100,
//            child: Image(
//              fit: BoxFit.scaleDown,
//              image: NetworkImage(
//                  "https://img.hbhcdn.com/dmp/s/merchant/1583251200/jh-img-orig-ga_1235068280189886464_1563_1172_1802512.jpg"),
//            ),
          );
        },
        childCount: 3,
      ),
    );
  }

  Widget _sliverGrid() {
    return SliverGrid(
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 3,
        childAspectRatio: 1,
        crossAxisSpacing: 10,
        mainAxisSpacing: 20,
      ),
      delegate: SliverChildBuilderDelegate(
        (BuildContext context, int index) {
          return Container(
            color: Colors.black,
            child: Image(
              fit: BoxFit.scaleDown,
              image: AssetImage("assets/images/login_guide_img_one.png"),
            ),
          );
        },
        childCount: 10,
      ),
    );
  }

  Widget _sliverToBoxAdapter() {
    return SliverToBoxAdapter(
      child: Container(
        color: Colors.amber,
        height: 200,
        child: Image(
          fit: BoxFit.cover,
          image: AssetImage("images/mv_ic_invitation_list_template_card.png",package: MyApp.FLUTTER_INV),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: DecoratedBox(
        decoration: BoxDecoration(
          gradient: ColorUtil.getGradient(
              [Color(CommonColor.C_FC708A), Color(CommonColor.C_FEAC81)]),
        ),
        child: CustomScrollView(
          slivers: <Widget>[
            _sliverAppBar(),
            _sliverList(),
            _sliverGrid(),
            _sliverToBoxAdapter(),
          ],
        ),
      ),
    );
  }
}
