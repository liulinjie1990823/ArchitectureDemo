import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_middle/configs/common_color.dart';
import 'package:flutter_middle/widgets/common_widget.dart';

class ImagePage extends CommonTitleBarPage {
  @override
  Widget buildChild(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: <Widget>[
          Container(
            width: double.infinity,
            alignment: Alignment.center, //不设置Image会占用Container的最大宽度
            color: CommonColor.COMMON_MAIN_COLOR,
            child: ClipOval(
              child: Image.network(
                'https://pic2.zhimg.com/v2-639b49f2f6578eabddc458b84eb3c6a1.jpg',
                width: 120,
                height: 120,
                fit: BoxFit.cover,
              ),
            ),
          ),
          Container(
            width: double.infinity,
            color: CommonColor.COMMON_MAIN_COLOR,
            child: ClipOval(
              child: Image.network(
                'https://pic2.zhimg.com/v2-639b49f2f6578eabddc458b84eb3c6a1.jpg',
                width: 120,
                height: 120,
                fit: BoxFit.cover,
              ),
            ),
          ),
          Container(
            width: double.infinity,
            alignment: Alignment.center,
            color: CommonColor.COMMON_MAIN_COLOR,
            child: CircleAvatar(
              //头像半径
              radius: 60,
              //头像图片 -> NetworkImage网络图片，AssetImage项目资源包图片, FileImage本地存储图片
              backgroundImage: NetworkImage(
                  'https://pic2.zhimg.com/v2-639b49f2f6578eabddc458b84eb3c6a1.jpg'),
            ),
          ),
          Container(
            width: 120,
            height: 120,
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              image: DecorationImage(
                  image: NetworkImage(
                      'https://pic2.zhimg.com/v2-639b49f2f6578eabddc458b84eb3c6a1.jpg'),
                  fit: BoxFit.cover),
            ),
          ),
          Container(
            width: double.infinity,
            color: CommonColor.COMMON_MAIN_COLOR,
            alignment: Alignment.center, //不设置Image会占用Container的最大宽度
            child: ClipRRect(
                borderRadius: BorderRadius.circular(8),
                child: Image.network(
                  'https://pic2.zhimg.com/v2-639b49f2f6578eabddc458b84eb3c6a1.jpg',
                  width: 120,
                  height: 120,
                  fit: BoxFit.cover,
                )),
          ),
          Container(
            width: 120,
            height: 120,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(8),
              image: DecorationImage(
                  image: NetworkImage(
                      'https://pic2.zhimg.com/v2-639b49f2f6578eabddc458b84eb3c6a1.jpg')),
            ),
          ),
          //斜切角形状示例
          Container(
            width: double.infinity,
            color: CommonColor.COMMON_MAIN_COLOR,
            alignment: Alignment.center,
            child: Container(
              width: 120,
              height: 120,
              decoration: ShapeDecoration(
                shape: BeveledRectangleBorder(
                  side: BorderSide(),
                  borderRadius: BorderRadius.circular(16),
                ),
                image: DecorationImage(
                    fit: BoxFit.cover,
                    image: NetworkImage(
                        'https://pic2.zhimg.com/v2-639b49f2f6578eabddc458b84eb3c6a1.jpg')),
              ),
            ),
          ),
          Container(
            width: double.infinity,
            height: 100,
            color: CommonColor.COMMON_MAIN_COLOR,
            child: Stack(
              alignment: Alignment.center,
              fit: StackFit.loose,
              children: <Widget>[
                Container(
                  width: 80,
                  height: 80,
                  decoration: ShapeDecoration(
                    shape: RoundedRectangleBorder(
                      side: BorderSide(),
                      borderRadius: BorderRadius.circular(16),
                    ),
                    image: DecorationImage(
                        fit: BoxFit.cover,
                        image: NetworkImage(
                            'https://pic2.zhimg.com/v2-639b49f2f6578eabddc458b84eb3c6a1.jpg')),
                  ),
                ),
                Positioned(
                  left: 0,
                  top: 0,
                  bottom: 0,
                  child: Align(
                    child: Text(
                      "RoundedRectangleBorder",
                      textAlign: TextAlign.center,
                    ),
                  ),
                ),
              ],
            ),
          ),
          Container(
            width: double.infinity,
            height: 100,
            color: CommonColor.COMMON_MAIN_COLOR,
            child: Stack(
              alignment: Alignment.center,
              fit: StackFit.loose,
              children: <Widget>[
                Container(
                  width: 80,
                  height: 80,
                  decoration: ShapeDecoration(
                    shape: CircleBorder(
                      side: BorderSide(),
                    ),
                    image: DecorationImage(
                      fit: BoxFit.cover,
                      image: NetworkImage(
                          'https://pic2.zhimg.com/v2-639b49f2f6578eabddc458b84eb3c6a1.jpg'),
                    ),
                  ),
                ),
                Positioned(
                  left: 0,
                  top: 0,
                  bottom: 0,
                  child: Align(
                    child: Text(
                      "CircleBorder",
                      textAlign: TextAlign.center,
                    ),
                  ),
                ),
              ],
            ),
          ),
          Container(
            width: double.infinity,
            height: 100,
            color: CommonColor.COMMON_MAIN_COLOR,
            child: Stack(
              alignment: Alignment.center,
              fit: StackFit.loose,
              children: <Widget>[
                Container(
                  width: 100,
                  height: 50,
                  decoration: ShapeDecoration(
                    shape: StadiumBorder(
                      side: BorderSide(),
                    ),
                    image: DecorationImage(
                      fit: BoxFit.cover,
                      image: NetworkImage(
                          'https://pic2.zhimg.com/v2-639b49f2f6578eabddc458b84eb3c6a1.jpg'),
                    ),
                  ),
                ),
                Positioned(
                    left: 0,
                    top: 0,
                    bottom: 0,
                    child: Align(
                      child: Text(
                        "StadiumBorder",
                        textAlign: TextAlign.center,
                      ),
                    )),
              ],
            ),
          ),
          Container(
            width: double.infinity,
            alignment: Alignment.center,
            color: CommonColor.COMMON_MAIN_COLOR,
            child: new Container(
              width: 100.0,
              height: 50.0,
              child: new RaisedButton(
                onPressed: () {},
                color: Colors.grey,
                child: new Text("登陆"),
                shape: new StadiumBorder(
                    side: new BorderSide(
                  style: BorderStyle.solid,
                  color: Color(0xffFF7F24),
                )),
              ),
            ),
          ),
        ],
      ),
    );
  }

  @override
  String centerWidgetText(BuildContext context) {
    return "image";
  }
}
