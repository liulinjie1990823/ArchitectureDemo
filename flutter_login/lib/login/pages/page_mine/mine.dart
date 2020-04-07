import 'package:flutter/material.dart';

class MinePage extends StatelessWidget {
  Widget _sliverAppBar() {
    return SliverAppBar(
      title: Text('SliverAppBar'),
      backgroundColor: Colors.blueAccent,
      expandedHeight: 200.0,
      flexibleSpace: FlexibleSpaceBar(
        background: Image(
          fit: BoxFit.cover,
          image: AssetImage("assets/images/login_guide_img_one.png"),
        ),
      ),
    );
  }

  Widget _sliverList() {
    return SliverList(
      delegate: SliverChildBuilderDelegate(
        (BuildContext context, int index) {
          return Container(
            child: Image(
              fit: BoxFit.scaleDown,
              image: NetworkImage(
                  "https://img.hbhcdn.com/dmp/s/merchant/1583251200/jh-img-orig-ga_1235068280189886464_1563_1172_1802512.jpg"),
            ),
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
              image: AssetImage("assets/images/login_guide_img_two.png"),
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
          image: AssetImage("assets/images/login_guide_img_three.png"),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.brown,
      body: CustomScrollView(
        slivers: <Widget>[
          _sliverAppBar(),
          _sliverList(),
          _sliverGrid(),
          _sliverToBoxAdapter(),
        ],
      ),
    );
  }
}
