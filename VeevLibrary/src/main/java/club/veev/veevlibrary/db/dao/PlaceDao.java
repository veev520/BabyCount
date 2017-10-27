package club.veev.veevlibrary.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import club.veev.veevlibrary.bean.Person;
import club.veev.veevlibrary.bean.Place;
import club.veev.veevlibrary.db.CountDBOpenHelper;
import club.veev.veevlibrary.utils.WLog;

/**
 * Created by Veev on 2017/10/18
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    PersonDao
 */
public class PlaceDao {

    private static final String TAG = "PlaceDao";

    private static String TABLE_NAME = "Place";

    private static String COLUMN_ID = "_id";
    private static String COLUMN_NAME = "name";
    private static String COLUMN_DESC = "desc";
    private static String COLUMN_LOCATION = "location";
    private static String COLUMN_CREATED = "createdAt";
    private static String COLUMN_UPDATED = "updatedAt";

    public static final String CREATE_TABLE_COUNT = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID               +   " integer primary key autoincrement," +
            COLUMN_NAME             +   " text,"        +
            COLUMN_LOCATION         +   " text,"        +
            COLUMN_CREATED          +   " real,"        +
            COLUMN_UPDATED          +   " real,"        +
            COLUMN_DESC             +   " text)";

    public List<Place> getAll() {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " desc");
        List<Place> list;

        if (cursor.moveToFirst()) {
            list = new ArrayList<>();

            do {
                int cid = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC));
                String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
                long createdAt = cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED));
                long updatedAt = cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED));
                list.add(new Place(cid, name, desc, location, createdAt, updatedAt));
            } while (cursor.moveToNext());
        } else {
            list = Collections.emptyList();
        }

        cursor.close();
        WLog.i(TAG, "getAll: " + list);
        return list;
    }

    public long insert(String name, String desc, String location) {
        long time = System.currentTimeMillis();
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_LOCATION, location);
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

    /**
     * 是否有这个名称的地点
     * @param name      分类名
     * @return          false       未找到
     *                   true       存在
     */
    public boolean hasName(String name) {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_NAME + " = ?", new String[]{name}, null, null, null);
        boolean hasMe = cursor.moveToFirst();
        cursor.close();
        return hasMe;
    }

    private int deleteAllNames(String name) {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[] {name});
    }

    public Place getPlace(int id) {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{"" + id}, null, null, null);
        Place person = null;

        if (cursor.moveToFirst()) {
            int cid = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC));
            String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
            long createdAt = cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED));
            long updatedAt = cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED));
            person = new Place(cid, name, desc, location, createdAt, updatedAt);
        }

        cursor.close();
        WLog.i(TAG, "getPlace: " + person);
        return person;
    }
}
