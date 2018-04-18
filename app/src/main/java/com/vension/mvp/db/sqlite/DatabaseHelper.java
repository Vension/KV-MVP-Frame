package com.vension.mvp.db.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vension.frame.VFrame;
import com.vension.mvp.db.sqlite.table.MediaChannelTable;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/3 14:42
 * 描  述：
 * ========================================================
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "HeadLine";
    private static final int DB_VERSION = 5;
    private static final String CLEAR_TABLE_DATA = "delete from ";
    private static final String DROP_TABLE = "drop table if exists ";
    private static DatabaseHelper instance = null;
    private static SQLiteDatabase db = null;

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static synchronized DatabaseHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseHelper(VFrame.getContext(), DB_NAME, null, DB_VERSION);
        }
        return instance;
    }

    public static synchronized SQLiteDatabase getDatabase() {
        if (db == null) {
            db = getInstance().getWritableDatabase();
        }
        return db;
    }

    public static synchronized void closeDatabase() {
        if (db != null) {
            db.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(NewsChannelTable.CREATE_TABLE);
        db.execSQL(MediaChannelTable.CREATE_TABLE);
//        db.execSQL(SearchHistoryTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(MediaChannelTable.CREATE_TABLE);
                break;
            case 2:
//                db.execSQL(CLEAR_TABLE_DATA + NewsChannelTable.TABLENAME);
                break;
            case 3:
//                ContentValues values = new ContentValues();
//                values.put(NewsChannelTable.ID, "");
//                values.put(NewsChannelTable.NAME, "推荐");
//                values.put(NewsChannelTable.IS_ENABLE, 0);
//                values.put(NewsChannelTable.POSITION, 46);
//                db.insert(NewsChannelTable.TABLENAME, null, values);
                break;
            case 4:
//                db.execSQL(SearchHistoryTable.CREATE_TABLE);
                break;
        }
    }
}
