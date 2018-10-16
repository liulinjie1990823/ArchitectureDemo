package com.llj.component.service.utils

import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.text.Spanned

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/15
 */
class CharInputFilter : InputFilter {

    //默认允许所有输入
    private var filterModel = 0xFF

    //限制输入的最大字符数, 小于0不限制
    private var maxInputLength = -1


    constructor() {}

    constructor(filterModel: Int) {
        this.filterModel = filterModel
    }

    fun setFilterModel(filterModel: Int) {
        this.filterModel = filterModel
    }

    fun setMaxInputLength(maxInputLength: Int) {
        this.maxInputLength = maxInputLength
    }

    /**
     * 将 dest 字符串中[dstart, dend] 位置对应的字符串, 替换成 source 字符串中 [start, end] 位置对应的字符串.
     *
     */
    override fun filter(source: CharSequence, //本次需要更新的字符串, (可以理解为输入法输入的字符,比如:我是文本)
                        start: Int, //取 source 字符串的开始位置,通常是0
                        end: Int, //取 source 字符串的结束位置,通常是source.length()
                        dest: Spanned, //原始字符串
                        dstart: Int, //原始字符串开始的位置,
                        dend: Int //原始字符串结束的位置, 这种情况会在你已经选中了很多个字符, 然后用输入法输入字符的情况下.
    ): CharSequence {
        //此次操作后, 原来的字符数量
        val length = dest.length - (dend - dstart)
        if (maxInputLength > 0) {
            if (length == maxInputLength) {
                return ""
            }
        }

        val modification = SpannableStringBuilder()

        for (i in start until end) {
            val c = source[i]

            var append = false

            if (filterModel and MODEL_CHINESE == MODEL_CHINESE) {
                append = isChinese(c) || append
            }
            if (filterModel and MODEL_CHAR_LETTER == MODEL_CHAR_LETTER) {
                append = isCharLetter(c) || append
            }
            if (filterModel and MODEL_NUMBER == MODEL_NUMBER) {
                append = isNumber(c) || append
            }
            if (filterModel and MODEL_ASCII_CHAR == MODEL_ASCII_CHAR) {
                append = isAsciiChar(c) || append
            }

            if (append) {
                modification.append(c)
            }
        }

        if (maxInputLength > 0) {

            val newLength = length + modification.length
            if (newLength > maxInputLength) {
                //越界
                modification.delete(maxInputLength - length, modification.length)
            }
        }

        return modification //返回修改后, 允许输入的字符串. 返回null, 由系统处理.
    }

    companion object {

        //允许中文输入
        val MODEL_CHINESE = 1

        //允许输入大小写字母
        val MODEL_CHAR_LETTER = 2

        //允许输入数字
        val MODEL_NUMBER = 4

        //允许输入Ascii码表的[33-126]的字符
        val MODEL_ASCII_CHAR = 8

        /**
         * 是否是中文
         */
        fun isChinese(c: Char): Boolean {
            val ub = Character.UnicodeBlock.of(c)
            return (ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                    || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION
                    || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                    || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
        }

        /**
         * 是否是大小写字母
         */
        fun isCharLetter(c: Char): Boolean {
            // Allow [a-zA-Z]
            if ('a' <= c && c <= 'z')
                return true
            return if ('A' <= c && c <= 'Z') true else false
        }

        fun isNumber(c: Char): Boolean {
            return '0' <= c && c <= '9'
        }

        fun isAsciiChar(c: Char): Boolean {
            return 33 <= c.toInt() && c.toInt() <= 126
        }
    }
}
