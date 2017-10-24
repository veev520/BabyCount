package club.veev.veevlibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import club.veev.veevlibrary.VeevLibraryModule;
import club.veev.veevlibrary.db.dao.CategoryDao;
import club.veev.veevlibrary.db.dao.PlaceDao;
import club.veev.veevlibrary.db.dao.RecordDao;
import club.veev.veevlibrary.db.dao.PersonDao;
import club.veev.veevlibrary.utils.WLog;

/**
 * Created by Veev on 2017/10/11
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    CountDBOpenHelper
 */

public class CountDBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "CountDBOpenHelper";

    private static final String DB_NAME = "baby_count.db";
    private static final int DB_VERSION = 1;

    private static CountDBOpenHelper sInst = null;

    public static CountDBOpenHelper getDefault() {
        if (sInst == null) {
            synchronized (CountDBOpenHelper.class) {
                if (sInst == null) {
                    sInst = new CountDBOpenHelper(VeevLibraryModule.getApp());
                }
            }
        }

        return sInst;
    }

    private CountDBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CategoryDao.CREATE_TABLE_CATEGORY);
        sqLiteDatabase.execSQL(RecordDao.CREATE_TABLE_COUNT);
        sqLiteDatabase.execSQL(PersonDao.CREATE_TABLE_COUNT);
        sqLiteDatabase.execSQL(PlaceDao.CREATE_TABLE_COUNT);
        WLog.i(TAG, "CREATE_TABLE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
