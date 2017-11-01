package club.veev.veevlibrary.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import club.veev.veevlibrary.bean.Category;
import club.veev.veevlibrary.db.CountDBOpenHelper;
import club.veev.veevlibrary.utils.WLog;

/**
 * Created by Veev on 2017/10/11
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    CategoryDao
 */
public class CategoryDao {

    private static final String TAG = "CategoryDao";

    private static String TABLE_NAME = "Category";
    private static String COLUMN_ID = "_id";
    private static String COLUMN_NAME = "name";
    private static String COLUMN_DESC = "desc";
    private static String COLUMN_UNIT = "unit";
    private static String COLUMN_TYPE = "type";
    private static String COLUMN_COVER = "cover";
    private static String COLUMN_CREATED = "createdAt";
    private static String COLUMN_UPDATED = "updatedAt";

    /**
     * 普通类型
     */
    public static final int TYPE_NORMAL = 0;
    /**
     * 单次事件
     */
    public static final int TYPE_SINGLE = 1;

    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID               +   " integer primary key autoincrement," +
            COLUMN_NAME             +   " text,"    +
            COLUMN_DESC             +   " text,"    +
            COLUMN_COVER            +   " text,"    +
            COLUMN_TYPE             +   " integer," +
            COLUMN_CREATED          +   " real,"    +
            COLUMN_UPDATED          +   " real,"    +
            COLUMN_UNIT             +   " text)";

    public List<Category> getAll() {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        List<Category> list;

        if (cursor.moveToFirst()) {
            list = new ArrayList<>();

            do {
                int cid = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC));
                String unit = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT));
                long createdAt = cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED));
                long updatedAt = cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED));
                list.add(new Category(cid, name, desc, unit, createdAt, updatedAt));
            } while (cursor.moveToNext());
        } else {
            list = Collections.emptyList();
        }

        cursor.close();
        WLog.i(TAG, "getAll: " + list);
        return list;
    }

    public int deleteAllNames(String name) {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[] {name});
    }

    /**
     * 是否有这个名称的分类
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

    public int count() {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getLastId() {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " desc");
        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        return id;
    }

    public void insert(String name, String desc, String unit) {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_UNIT, unit);
        values.put(COLUMN_CREATED, System.currentTimeMillis());
        values.put(COLUMN_UPDATED, System.currentTimeMillis());

        deleteAllNames(name);
        db.insert(TABLE_NAME, null, values);
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = CountDBOpenHelper.getDefault().getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{"" + id}, null, null, null);
        Category category = null;

        if (cursor.moveToFirst()) {
            int cid = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC));
            String unit = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT));
            long createdAt = cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED));
            long updatedAt = cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED));
            category = new Category(cid, name, desc, unit, createdAt, updatedAt);
        }

        cursor.close();
        WLog.i(TAG, "getCategory: " + category);
        return category;
    }
}
