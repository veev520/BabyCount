package club.veev.veevlibrary.utils;

import android.text.TextUtils;

/**
 * Created by Veev on 2017/10/19
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    WString
 */

public class WString {

    /**
     * 获取不为 null 的字符串
     * 由此避免空指针
     */
    public static String getNotNullString(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    /**
     * 转换成单行显示的 字符串
     * 就是将 换行 换成 空格
     *
     * 转换前:      Hello
     *              World
     *              1
     *              2
     *              666
     *
     * 转换后:      Hello World 1 2 666
     */
    public static String convert2SingleLine(String str) {
        return str.replaceAll("\n", "\b");
    }

    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !TextUtils.isEmpty(str);
    }
}
