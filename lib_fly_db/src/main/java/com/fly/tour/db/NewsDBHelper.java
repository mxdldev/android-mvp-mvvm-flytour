package com.fly.tour.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Description: <NewsDBHelper><br>
 * Author:      mxdl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDBHelper extends SQLiteOpenHelper {
    private static NewsDBHelper mNewsDBHelper;
    public static NewsDBHelper getInstance(Context context) {
        if (mNewsDBHelper == null) {
            synchronized (NewsDBHelper.class) {
                mNewsDBHelper = new NewsDBHelper(context);
            }
        }
        return mNewsDBHelper;
    }

    private NewsDBHelper(Context context) {
        super(context, NewsDBConfig.DB_NAME, null, NewsDBConfig.VERSION_CODE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NewsDBConfig.NewsType.CREATE_TABLE);
        db.execSQL(NewsDBConfig.NewsDetail.CREATE_TABLE);
    }
}
