package club.veev.veevlibrary.db;

import android.database.sqlite.SQLiteDatabase;

import club.veev.veevlibrary.db.dao.CategoryDao;
import club.veev.veevlibrary.db.dao.PersonDao;
import club.veev.veevlibrary.db.dao.PlaceDao;
import club.veev.veevlibrary.db.dao.RecordDao;

/**
 * Created by Veev on 2017/10/27
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    DaoSession
 */

public class DaoSession {

    private CategoryDao mCategoryDao;
    private PersonDao mPersonDao;
    private PlaceDao mPlaceDao;
    private RecordDao mRecordDao;

    public DaoSession() {
        mCategoryDao = new CategoryDao();
        mPersonDao = new PersonDao();
        mPlaceDao = new PlaceDao();
        mRecordDao = new RecordDao();
    }

    public CategoryDao getCategoryDao() {
        return mCategoryDao;
    }

    public PersonDao getPersonDao() {
        return mPersonDao;
    }

    public PlaceDao getPlaceDao() {
        return mPlaceDao;
    }

    public RecordDao getRecordDao() {
        return mRecordDao;
    }
}
