package club.veev.veevlibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.ColorInt;

/**
 * Created by Veev on 2017/10/21
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    Bitmap 工具类
 */
public class WBitmap {
    /**
     * 改变 bitmap 的颜色
     * 图片空白区域必须为透明
     * 改变后的 bitmap 是纯色
     *
     * @param src   源图片
     * @param color 需要改变的颜色
     * @return 现图片
     */
    public static Bitmap changeBitmapColor(Bitmap src, @ColorInt int color) {
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawRect(0, 0, src.getWidth(), src.getHeight(), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(src, 0, 0, paint);
        return bitmap;
    }
}
