package club.veev.babycount;

import android.app.Application;

import club.veev.veevlibrary.VeevLibraryModule;

/**
 * Created by Veev on 2017/10/10
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    VeevLibraryModule
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initModule();
    }

    private void initModule() {
        VeevLibraryModule.init(this);
    }
}
