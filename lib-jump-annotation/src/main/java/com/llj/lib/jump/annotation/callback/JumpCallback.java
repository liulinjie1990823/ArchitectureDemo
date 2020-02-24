package com.llj.lib.jump.annotation.callback;

import java.util.Map;

/**
 * ArchitectureDemo.
 *
 * describe:
 *
 * @author llj
 * @date 2019-06-13
 */
public interface JumpCallback {

  String getInPath();

  void process(String paramOriginStr, Map<String, String> map);
}
