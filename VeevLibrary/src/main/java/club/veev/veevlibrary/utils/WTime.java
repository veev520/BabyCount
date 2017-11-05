package club.veev.veevlibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Veev on 2017/10/14
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    WTime
 *
 * <pre>
 *                                             HH:mm    15:44
 *                                            h:mm a    3:44 下午
 *                                           HH:mm z    15:44 CST
 *                                           HH:mm Z    15:44 +0800
 *                                        HH:mm zzzz    15:44 中国标准时间
 *                                          HH:mm:ss    15:44:40
 *                                        yyyy-MM-dd    2016-08-12
 *                                  yyyy-MM-dd HH:mm    2016-08-12 15:44
 *                               yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
 *                          yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
 *                     EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
 *                          yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
 *                        yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
 *                      yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
 *                                            K:mm a    3:44 下午
 *                                  EEE, MMM d, ''yy    星期五, 八月 12, '16
 *                             hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
 *                      yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
 *                        EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
 *                                     yyMMddHHmmssZ    160812154440+0800
 *                        yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
 * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
 */
public class WTime {

    public static String getFormatTime(String format) {
        long time = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    public static String getFormatTime(String format, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getFormatTime(String format, long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    public static String getShowTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    /**
     * 获取年龄
     * @param birthYear         出生年份
     * @param birthMonth        出生月份
     * @param birthDay          出生日
     *
     * @return                  1岁 2月 3天
     */
    public static String getAge(int birthYear, int birthMonth, int birthDay) {
        Calendar birth = Calendar.getInstance();
        birth.set(Calendar.YEAR, birthYear);
        birth.set(Calendar.MONTH, birthMonth);
        birth.set(Calendar.DAY_OF_MONTH, birthDay);
        Calendar now = Calendar.getInstance();
        int deltaDay = now.get(Calendar.DAY_OF_MONTH) - birth.get(Calendar.DAY_OF_MONTH) + 1;
        int deltaMonth = now.get(Calendar.MONTH) - birth.get(Calendar.MONTH);
        int deltaYear = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        if (deltaDay < 0) {
            deltaDay += birth.getActualMaximum(Calendar.DATE);//得到一个月最大的一天就是一个月多少天
            deltaMonth -= 1;
        }

        if (deltaMonth < 0) {
            deltaYear -= 1;
        }

        if (deltaYear == 0) {
            if (deltaMonth == 0) {
                return String.format(Locale.CHINA, "%1$d天", deltaDay);
            }
            return String.format(Locale.CHINA, "%1$d月 %2$d天", deltaMonth, deltaDay);
        }
        return String.format(Locale.CHINA, "%1$d岁 %2$d月 %3$d天", deltaYear, deltaMonth, deltaDay);
    }
}
