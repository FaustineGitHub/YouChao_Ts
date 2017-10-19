package com.youchao.tingshuo.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

/**
 * 输入工具类
 * Created by Dlt on 2017/7/26 16:28
 */
public class CommonInputUtils {

    /**
     * 格式化字符串(四舍五入保留两位小数)
     */
    public static String formattingString(String orginalString) {

        double d = Double.parseDouble(orginalString);
        String result = String.format("%.2f", d);// %.2f %. 表示 小数点前任意位数 2 表示两位小数 格式后的结果为f 表示浮点型。

        return result;
    }

    /**
     * 过滤空格
     *
     * @param editText
     */
    public static void filterBlank(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                //返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        };

        editText.setFilters(new InputFilter[]{filter});

    }

    /**
     * 过滤空格和换行符
     *
     * @param editText
     */
    public static void filterBlankAndLineBreak(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                //返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
                if (source.equals(" ") || source.toString().contentEquals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }
        };

        editText.setFilters(new InputFilter[]{filter});

    }

}
