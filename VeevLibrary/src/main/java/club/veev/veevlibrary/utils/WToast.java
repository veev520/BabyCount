package club.veev.veevlibrary.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import club.veev.veevlibrary.VeevLibraryModule;

/**
 * Created by Veev on 2017/6/30.
 * QQ: 384506557
 * Fun: WToast
 */
public class WToast {

    private static Toast sToast;

    public static void show(String msg) {

        if (sToast == null) {
            sToast = Toast.makeText(VeevLibraryModule.getApp(), msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);
        }

        sToast.show();
    }

    public static void show(@StringRes int msg) {

        if (sToast == null) {
            sToast = Toast.makeText(VeevLibraryModule.getApp(), msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);
        }

        sToast.show();
    }
}
