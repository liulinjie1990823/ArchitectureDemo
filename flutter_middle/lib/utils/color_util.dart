import 'dart:ui';

import 'package:flutter/material.dart';

class ColorUtil {
  static Gradient getGradient(List<Color> colors) {
    return LinearGradient(
        begin: Alignment.centerLeft,
        end: Alignment.centerRight,
        colors: colors);
  }
}
