package club.veev.veevlibrary;

import android.app.Application;

/**
 * Created by Veev on 2017/10/11
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    VeevLibraryModule
 */

public class VeevLibraryModule {

    private static Application sApplication = null;

    public static void init(Application app) {
        sApplication = app;
    }

    public static Application getApp() {
        if (sApplication == null) {
            throw new IllegalStateException("have to call init before used");
        }
        return sApplication;
    }
}
