package club.veev.veevlibrary.utils;

import android.util.Log;

import java.util.Collection;

/**
 * Created by Veev on 2017/6/30.
 * QQ: 384506557
 * Fun: WToast
 */
public class WLog {
    private static final boolean isDebug = true;

    public static int i(String tag, String msg) {
        return isDebug ? Log.i(tag, msg) : -1;
    }
    public static int w(String tag, String msg) {
        return isDebug ? Log.w(tag, msg) : -1;
    }
    public static int i(String tag, Object msg) {
        return isDebug ? Log.i(tag, msg.toString()) : -1;
    }
    public static int i(String tag, Object... args) {
        return isDebug ? Log.i(tag, getLog(args)) : -1;
    }
    public static <T extends Collection> int i(String tag , T t) {
        return isDebug ? Log.i(tag, getLog(t)) : -1;
    }

    public static int d(String tag, String msg) {
        return isDebug ? Log.d(tag, msg) : -1;
    }

    public static int v(String tag, String msg) {
        return isDebug ? Log.v(tag, msg) : -1;
    }

    public static int e(String tag, String msg) {
        return isDebug ? Log.e(tag, msg) : -1;
    }

    public static int e(String tag, String msg, Throwable r) {
        return isDebug ? Log.e(tag, msg, r) : -1;
    }


    public static int e(String tag, Object msg) {
        return isDebug ? Log.e(tag, msg.toString()) : -1;
    }

    public static int j(String tag, String msg) {
        return isDebug ? Log.i(tag, toJsonStr(msg)) : -1;
    }
    public static int j(String tag, Object msg) {
        return isDebug ? Log.i(tag, toJsonStr(msg.toString())) : -1;
    }

    private static String toJsonStr(String jsonStr) {
        int level = 0;
        StringBuilder jsonForMatStr = new StringBuilder();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c).append("\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c).append("\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level) {
        StringBuilder levelStr = new StringBuilder();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    private static String getLog(Object... args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : args) {
            if (o != null) {
                sb.append(o.toString());
            } else {
                sb.append("Null Object");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static <T extends Collection> String getLog(T t) {
        if (t == null || t.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : t) {
            if (o != null) {
                sb.append(o.toString());
            } else {
                sb.append("Null Object");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
