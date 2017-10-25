package club.veev.veevlibrary.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import club.veev.veevlibrary.bean.Category;
import club.veev.veevlibrary.bean.Place;
import club.veev.veevlibrary.bean.Record;
import club.veev.veevlibrary.bean.Person;
import club.veev.veevlibrary.db.CountDBOpenHelper;
import club.veev.veevlibrary.utils.WLog;

/**
 * Created by Veev on 2017/10/15
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    RecordDao
 */

public class RecordDao {
    private static final String TAG = "RecordDao";

    private static String TABLE_NAME = "Record";
    private static String COLUMN_ID = "_id";
    private static String COLUMN_TITLE = "title";
    private static String COLUMN_DESC = "desc";
    private static String COLUMN_UNIT = "unit";
    private static String COLUMN_VALUE = "value";
    private static String COLUMN_TIME = "time";
    private static String COLUMN_CREATED = "createdAt";
    private static String COLUMN_UPDATED = "updatedAt";
    private static String COLUMN_PLACE_ID = "place_id";
    private static String COLUMN_TARGET_ID = "target_id";
    private static String COLUMN_SOURCE_ID = "source_id";
    private static String COLUMN_CATEGORY_ID = "category_id";

    public static final String CREATE_TABLE_COUNT = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID               +   " integer primary key autoincrement," +
            COLUMN_CATEGORY_ID      +   " integer,"     +
            COLUMN_TARGET_ID        +   " integer,"     +
            COLUMN_SOURCE_ID        +   " integer,"     +
            COLUMN_PLACE_ID         +   " integer,"     +
            COLUMN_TITLE            +   " text,"        +
            COLUMN_UNIT             +   " text,"        +
            COLUMN_VALUE            +   " real,"        +
            COLUMN_TIME             +   " real,"        +
            COLUMN_CREATED          +   " real,"        +
            COLUMN_UPDATED          +   " real,"        +
            COLUMN_DESC             +   " text)";

    public List<Record> getRecordsByCategory(int categoryId) {
        return getAllRecord(COLUMN_CATEGORY_ID + " = ?", new String[]{categoryId + ""});
    }

    public List<Record> getAll() {
        return getAllRecord(null, null);
    }

    public List<Record> getAllRecord(String selection, String[] selectionArgs) {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, COLUMN_ID + " desc");
        List<Record> list;

        if (cursor.moveToFirst()) {
            list = new ArrayList<>();

            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));

                int categoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                Category category = new CategoryDao().getCategory(categoryId);
                int targetId = cursor.getInt(cursor.getColumnIndex(COLUMN_TARGET_ID));
                int sourceId = cursor.getInt(cursor.getColumnIndex(COLUMN_SOURCE_ID));
                int place_id = cursor.getInt(cursor.getColumnIndex(COLUMN_PLACE_ID));

                PersonDao personDao = new PersonDao();
                Person target = personDao.getPerson(targetId);
                Person source = personDao.getPerson(sourceId);
                Place place = new PlaceDao().getPlace(place_id);

                float value = cursor.getFloat(cursor.getColumnIndex(COLUMN_VALUE));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC));
                String unit = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT));

                long time = cursor.getLong(cursor.getColumnIndex(COLUMN_TIME));
                long createdAt = cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED));
                long updatedAt = cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED));
                list.add(new Record(id, category, title, value, desc, unit, place, target, source, time, createdAt, updatedAt));
            } while (cursor.moveToNext());
        } else {
            list = Collections.emptyList();
        }

        cursor.close();
        WLog.i(TAG, "getAll: " + list);
        return list;
    }

    public void insert(Category category, String title, String desc, float value, int place, int target, int source, long time) {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_UNIT, category.getUnit());
        values.put(COLUMN_VALUE, value);
        values.put(COLUMN_CATEGORY_ID, category.getId());
        values.put(COLUMN_PLACE_ID, place);
        values.put(COLUMN_TARGET_ID, target);
        values.put(COLUMN_SOURCE_ID, source);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_CREATED, System.currentTimeMillis());
        values.put(COLUMN_UPDATED, System.currentTimeMillis());

        db.insert(TABLE_NAME, null, values);
    }
}
