import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter_middle/configs/common_color.dart';

class ColorUtil {
  static Gradient getGradient(List<Color> colors) {
    return LinearGradient(
        begin: Alignment.centerLeft,
        end: Alignment.centerRight,
        colors: colors);
  }

  static Gradient getInvGradient() {
    return LinearGradient(
        begin: Alignment.centerLeft,
        end: Alignment.centerRight,
        colors: [
          Color(CommonColor.C_MAIN_COLOR),
          Color(CommonColor.C_FF5442)
        ]);
  }
}
