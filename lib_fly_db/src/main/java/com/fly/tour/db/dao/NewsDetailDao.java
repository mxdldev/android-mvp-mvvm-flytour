package com.fly.tour.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fly.tour.db.NewsDBConfig;
import com.fly.tour.db.NewsDBHelper;
import com.fly.tour.db.entity.NewsDetail;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description: <NewsTypeDao><br>
 * Author:      gxl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailDao {
    private SQLiteDatabase mDatabase;

    @Inject
    public NewsDetailDao(Context context) {
        mDatabase = NewsDBHelper.getInstance(context).getReadableDatabase();
    }

    public boolean addNewsDetail(int type, String title, String content) {
        ContentValues values = new ContentValues();
        values.put(NewsDBConfig.NewsDetail.CLUMN_TYPE_ID, type);
        values.put(NewsDBConfig.NewsDetail.CLUMN_TITLE, title);
        values.put(NewsDBConfig.NewsDetail.CLUMN_CONTENT, content);
        return mDatabase.insert(NewsDBConfig.NewsDetail.TABLE_NAME, null, values) > 0;
    }

    public boolean deleteNewsDetail(int id) {
        return mDatabase.delete(NewsDBConfig.NewsDetail.TABLE_NAME, "where id = ?", new String[]{id + ""}) > 0;
    }

    public List<NewsDetail> getListNewsByType(int type) {
        List<NewsDetail> dataList = null;
        String sql = "select " + NewsDBConfig.NewsDetail.CLUMN_ID + "," + NewsDBConfig.NewsDetail.CLUMN_TITLE + "," + NewsDBConfig.NewsDetail.CLUMN_CONTENT + "," + NewsDBConfig.NewsDetail.CLUMN_ADD_TIME + " from " + NewsDBConfig.NewsDetail.TABLE_NAME + " where " + NewsDBConfig.NewsDetail.CLUMN_TYPE_ID + " = " + type;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            dataList = new ArrayList<>();
            while (cursor.moveToNext()) {
                NewsDetail detail = new NewsDetail();
                detail.setId(cursor.getInt(0));
                detail.setTitle(cursor.getString(1));
                detail.setContent(cursor.getString(2));
                detail.setAddtime(cursor.getString(3));
                dataList.add(detail);
            }
        }
        return dataList;
    }

    public boolean isEmpty() {
        String sql = "select * from " + NewsDBConfig.NewsDetail.TABLE_NAME;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            return false;
        }
        return true;
    }

    public void addListNewsDetail(List<NewsDetail> details) {
        if (details != null && details.size() > 0) {
            mDatabase.beginTransaction();
            for (NewsDetail detail : details) {
                addNewsDetail(detail.getTypeid(), detail.getTitle(), detail.getContent());
            }
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
            mDatabase.close();
        }
    }

    public NewsDetail getNewsDetailById(int id) {
        String sql = "select " + NewsDBConfig.NewsDetail.CLUMN_ID + "," + NewsDBConfig.NewsDetail.CLUMN_TITLE + "," + NewsDBConfig.NewsDetail.CLUMN_CONTENT + "," + NewsDBConfig.NewsDetail.CLUMN_ADD_TIME + " from " + NewsDBConfig.NewsDetail.TABLE_NAME + " where " + NewsDBConfig.NewsDetail.CLUMN_ID + " = " + id;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        NewsDetail detail = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToNext();
            detail = new NewsDetail();
            detail.setId(cursor.getInt(0));
            detail.setTitle(cursor.getString(1));
            detail.setContent(cursor.getString(2));
            detail.setAddtime(cursor.getString(3));
        }
        return detail;
    }
}
