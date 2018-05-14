package com.llj.lib.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * project:babyphoto_app
 * describe:
 * Created by llj on 2017/7/21.
 */

public class ATextWatcherUtils {


    public static TextWatcher getLengthFilter(final EditText editText, final int maxLength) {
        return new TextWatcher() {
            private int count = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (count > maxLength) {
                    int selectionEnd = editText.getSelectionEnd();
                    s.delete(maxLength, selectionEnd);
                }
            }
        };
    }
}
