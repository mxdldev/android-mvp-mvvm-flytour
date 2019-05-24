package com.fly.tour.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fly.tour.db.NewsDBConfig;
import com.fly.tour.db.NewsDBHelper;

/**
 * Description: <NewsTypeDao><br>
 * Author:      gxl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeDao {
    private SQLiteDatabase mDatabase;

    public NewsTypeDao(Context context) {
        mDatabase = NewsDBHelper.getInstance(context).getReadableDatabase();
    }

    public boolean addNewsType(String typename) {
        ContentValues values = new ContentValues();
        values.put(NewsDBConfig.NewsType.CLUMN_TYPE_NAME, typename);
        return mDatabase.insert(NewsDBConfig.NewsType.TABLE_NAME, null, values) > 0;
    }
    public boolean deleteNewsType(int id){
       return mDatabase.delete(NewsDBConfig.NewsType.TABLE_NAME,"where id = ?",new String[]{id+""}) > 0;
    }
}
