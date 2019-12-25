import 'dart:ui';

import 'package:flutter/material.dart';

class DisplayUtils {
  static MediaQueryData mediaQuery = MediaQueryData.fromWindow(window);
  static double _width = mediaQuery.size.width;
  static double _height = mediaQuery.size.height;
  static double _topbarH = mediaQuery.padding.top;
  static double _botbarH = mediaQuery.padding.bottom;
  static double pixelRatio = mediaQuery.devicePixelRatio;
}
