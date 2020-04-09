import 'package:flutter/material.dart';

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
