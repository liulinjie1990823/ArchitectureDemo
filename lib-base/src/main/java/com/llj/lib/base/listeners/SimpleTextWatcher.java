package com.llj.lib.base.listeners;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * describe:EditText输入监听
 *
 * @author liulj
 * @date 2018/1/3
 */

public abstract class SimpleTextWatcher implements TextWatcher {

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void afterTextChanged(Editable s) {

  }
}
