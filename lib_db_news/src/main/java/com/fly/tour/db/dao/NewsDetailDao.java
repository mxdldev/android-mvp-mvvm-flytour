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
public class NewsDetailDao {
    private SQLiteDatabase mDatabase;

    public NewsDetailDao(Context context) {
        mDatabase = NewsDBHelper.getInstance(context).getReadableDatabase();
    }

    public boolean addNewsDetail(int type,String title,String content) {
        ContentValues values = new ContentValues();
        values.put(NewsDBConfig.NewsDetail.CLUMN_TYPE_ID, type);
        values.put(NewsDBConfig.NewsDetail.CLUMN_TITLE, title);
        values.put(NewsDBConfig.NewsDetail.CLUMN_CONTENT, content);
        return mDatabase.insert(NewsDBConfig.NewsDetail.TABLE_NAME, null, values) > 0;
    }
    public boolean deleteNewsDetail(int id){
       return mDatabase.delete(NewsDBConfig.NewsDetail.TABLE_NAME,"where id = ?",new String[]{id+""}) > 0;
    }
}
