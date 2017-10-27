package club.veev.babycount;

import android.app.Application;

import club.veev.veevlibrary.VeevLibraryModule;
import club.veev.veevlibrary.db.DaoSession;

/**
 * Created by Veev on 2017/10/10
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    VeevLibraryModule
 */

public class App extends Application {

    private static App sApp;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        sApp = this;

        initModule();

        mDaoSession = new DaoSession();
    }

    private void initModule() {
        VeevLibraryModule.init(this);
    }

    public static App getApp() {
        return sApp;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
