import 'package:flutter/material.dart';

class InvHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Align(
            alignment: Alignment.topCenter,
            child: Image.network(
                "https://i.loli.net/2019/11/30/auRxklc9f6TLgdE.jpg"),
          ),
          DraggableScrollableSheet(
            initialChildSize:
                0.6, // 设置父容器的高度 1 ~ 0, initialChildSize必须 <= maxChildSize
            minChildSize:
                0.4, // 限制child最小高度, minChildSize必须 <= initialChildSize
            maxChildSize: 0.8, // 限制child最大高度,
            builder: (context, scrollController) {
              return Container(
                color: Colors.blue,
                child: SingleChildScrollView(
                  controller: scrollController,
                  child: Column(
                    children: <Widget>[
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Image.network(
                          "https://i.loli.net/2019/11/30/DVJGdj3eNpakXY4.jpg",
                          fit: BoxFit.cover,
                        ),
                      ),
                      for (var i = 0; i < 25; i++)
                        ListTile(title: Text('Item $i'))
                    ],
                  ),
                ),
              );
            },
          ),
        ],
      ),
    );
  }
}
