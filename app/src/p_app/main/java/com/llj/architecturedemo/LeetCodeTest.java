package com.llj.architecturedemo;

import java.util.HashSet;
import java.util.Set;

/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020/3/24 12:39 PM
 */
public class LeetCodeTest {

  public static void main(String[] strs) {
    String[] strings = {"flower", "flowdad", "flight"};
    System.out.println(longestCommonPrefix("pwwkew"));

  }


  public static int longestCommonPrefix(String s) {
    int n = s.length();
    Set<Character> set = new HashSet<>();
    int ans = 0, i = 0, j = 0;
    while (i < n && j < n) {
      // try to extend the range [i, j]
      if (!set.contains(s.charAt(j))) {
        set.add(s.charAt(j++));
        ans = Math.max(ans, j - i);
      } else {
        //char charAt = s.charAt(i++);
        set.remove(s.charAt(i++));
      }
    }
    return ans;
  }
}
