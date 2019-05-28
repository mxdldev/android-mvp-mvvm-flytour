package com.fly.tour.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fly.tour.db.NewsDBConfig;
import com.fly.tour.db.NewsDBHelper;
import com.fly.tour.db.entity.NewsType;

import java.util.ArrayList;
import java.util.List;

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

    public boolean deleteNewsType(int id) {
        return mDatabase.delete(NewsDBConfig.NewsType.TABLE_NAME, "id = ?", new String[]{id + ""}) > 0;
    }

    public List<NewsType> getListNewsType() {
        String query = "select * from " + NewsDBConfig.NewsType.TABLE_NAME;
        Cursor cursor = mDatabase.rawQuery(query, null);
        List<NewsType> typeList = null;
        if (cursor != null && cursor.getCount() > 0) {
            typeList = new ArrayList<>();
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String typename = cursor.getString(1);
                String addtime = cursor.getString(2);
                NewsType newsType = new NewsType();
                newsType.setId(id);
                newsType.setTypename(typename);
                newsType.setAddtime(addtime);
                typeList.add(newsType);
            }
        }
        return typeList;
    }
    public void addListNewStype(List<NewsType> newsTypeList){
        mDatabase.beginTransaction();
        for(NewsType type : newsTypeList){
            addNewsType(type.getTypename());
        }
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
        mDatabase.close();
    }
    public boolean isEmpty(){
        String sql = "select * from "+NewsDBConfig.NewsType.TABLE_NAME;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        if(cursor != null && cursor.getCount() > 0){
            return false;
        }
        return true;
    }
}
