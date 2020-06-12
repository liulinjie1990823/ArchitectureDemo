import 'dart:ui';

import 'package:flutter/material.dart';

class DisplayUtil {
  static MediaQueryData mediaQuery = MediaQueryData.fromWindow(window);
  static double width = mediaQuery.size.width;
  static double height = mediaQuery.size.height;
  static double _topbarH = mediaQuery.padding.top;
  static double _botbarH = mediaQuery.padding.bottom;
  static double pixelRatio = mediaQuery.devicePixelRatio;
}
