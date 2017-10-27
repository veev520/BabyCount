package club.veev.veevlibrary.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import club.veev.veevlibrary.bean.Person;
import club.veev.veevlibrary.db.CountDBOpenHelper;
import club.veev.veevlibrary.utils.WLog;

/**
 * Created by Veev on 2017/10/18
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    PersonDao
 */
public class PersonDao {

    private static final String TAG = "PersonDao";

    private static String TABLE_NAME = "Person";

    private static String COLUMN_ID = "_id";
    private static String COLUMN_NAME = "name";
    private static String COLUMN_DESC = "desc";
    private static String COLUMN_AVATAR = "avatar";
    private static String COLUMN_CREATED = "createdAt";
    private static String COLUMN_UPDATED = "updatedAt";

    public static final String CREATE_TABLE_COUNT = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID               +   " integer primary key autoincrement," +
            COLUMN_NAME             +   " text,"        +
            COLUMN_AVATAR           +   " text,"        +
            COLUMN_CREATED          +   " real,"        +
            COLUMN_UPDATED          +   " real,"        +
            COLUMN_DESC             +   " text)";

    public List<Person> getAll() {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " desc");
        List<Person> list;

        if (cursor.moveToFirst()) {
            list = new ArrayList<>();

            do {
                int cid = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC));
                String avatar = cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR));
                long createdAt = cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED));
                long updatedAt = cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED));
                list.add(new Person(cid, name, desc, avatar, createdAt, updatedAt));
            } while (cursor.moveToNext());
        } else {
            list = Collections.emptyList();
        }

        cursor.close();
        WLog.i(TAG, "getAll: " + list);
        return list;
    }

    public long insert(String name, String desc, String avatar) {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        long time = System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_AVATAR, avatar);
        values.put(COLUMN_CREATED, time);
        values.put(COLUMN_UPDATED, time);

        return db.insert(TABLE_NAME, null, values);
    }

    public int count() {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Person getPerson(int id) {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{"" + id}, null, null, null);
        Person person = null;

        if (cursor.moveToFirst()) {
            int cid = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC));
            String avatar = cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR));
            long createdAt = cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED));
            long updatedAt = cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED));
            person = new Person(cid, name, desc, avatar, createdAt, updatedAt);
        }

        cursor.close();
        WLog.i(TAG, "getPlace: " + person);
        return person;
    }
}
