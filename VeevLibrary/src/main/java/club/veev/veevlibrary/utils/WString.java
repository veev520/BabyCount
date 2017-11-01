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

    private static final String EMPTY_STR = "";
    //数字位
    public static String[] chnNumChar = {"零","一","二","三","四","五","六","七","八","九"};
    public static char[] chnNumChinese = {'零','一','二','三','四','五','六','七','八','九'};
    //节权位
    public static String[] chnUnitSection = {"","万","亿","万亿"};
    //权位
    public static String[] chnUnitChar = {"","十","百","千"};

    public static String convertInt2Ch(int num) {
        StringBuilder sb = new StringBuilder();

        String str = Integer.toString(num);
        for (int len = str.length(), i = 0; i < len; i++) {
            // 余数
            int remainder = (len - 1 - i) % 4;
            // 位数
            int bit = (len - 1 - i) / 4;
            // 该位上的数字
            int n = str.charAt(i) - '0';
            sb.append(chnNumChinese[n]);
            if (remainder == 0) {
                sb.append(chnUnitSection[bit]);
            } else if (n != 0){
                sb.append(chnUnitChar[remainder]);
            }
        }

        // 重复的零去掉
        String s = sb.toString();
        while (s.contains("零零")) {
            s = s.replaceAll("零零", "零");
        }
        return s;
    }

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
     * <p>
     * 转换前:      Hello
     * World
     * 1
     * 2
     * 666
     * <p>
     * 转换后:      Hello World 1 2 666
     */
    public static String convert2SingleLine(String str) {
        if (isEmpty(str)) {
            return EMPTY_STR;
        }
        return str.replaceAll("\n", "\b");
    }

    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !TextUtils.isEmpty(str);
    }
}
