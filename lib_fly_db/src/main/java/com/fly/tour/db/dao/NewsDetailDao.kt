package com.fly.tour.db.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import com.fly.tour.db.NewsDBConfig
import com.fly.tour.db.NewsDBHelper
import com.fly.tour.db.entity.NewsDetail

import java.util.ArrayList

/**
 * Description: <NewsTypeDao><br>
 * Author:      mxdl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsDetailDao(context: Context) {
    private val mDatabase: SQLiteDatabase
    init {
        mDatabase = NewsDBHelper.getInstance(context)!!.readableDatabase
    }
    val isEmpty: Boolean
        get() {
            val sql = "select * from " + NewsDBConfig.NewsDetail.TABLE_NAME
            val cursor = mDatabase.rawQuery(sql, null)
            return if (cursor != null && cursor.count > 0) {
                false
            } else true
        }

    fun addNewsDetail(type: Int, title: String, content: String): Boolean {
        val values = ContentValues()
        values.put(NewsDBConfig.NewsDetail.CLUMN_TYPE_ID, type)
        values.put(NewsDBConfig.NewsDetail.CLUMN_TITLE, title)
        values.put(NewsDBConfig.NewsDetail.CLUMN_CONTENT, content)
        return mDatabase.insert(NewsDBConfig.NewsDetail.TABLE_NAME, null, values) > 0
    }

    fun deleteNewsDetail(id: Int): Boolean {
        return mDatabase.delete(NewsDBConfig.NewsDetail.TABLE_NAME, "where id = ?", arrayOf(id.toString() + "")) > 0
    }

    fun getListNewsByType(type: Int): List<NewsDetail>? {
        var dataList: MutableList<NewsDetail>? = null
        val sql = "select " + NewsDBConfig.NewsDetail.CLUMN_ID + "," + NewsDBConfig.NewsDetail.CLUMN_TITLE + "," + NewsDBConfig.NewsDetail.CLUMN_CONTENT + "," + NewsDBConfig.NewsDetail.CLUMN_ADD_TIME + " from " + NewsDBConfig.NewsDetail.TABLE_NAME + " where " + NewsDBConfig.NewsDetail.CLUMN_TYPE_ID + " = " + type
        val cursor = mDatabase.rawQuery(sql, null)
        if (cursor != null && cursor.count > 0) {
            dataList = ArrayList()
            while (cursor.moveToNext()) {
                val detail = NewsDetail()
                detail.id = cursor.getInt(0)
                detail.title = cursor.getString(1)
                detail.content = cursor.getString(2)
                detail.addtime = cursor.getString(3)
                dataList.add(detail)
            }
        }
        return dataList
    }

    fun addListNewsDetail(details: List<NewsDetail>?) {
        if (details != null && details.size > 0) {
            mDatabase.beginTransaction()
            for (detail in details) {
                addNewsDetail(detail.typeid, detail.title, detail.content)
            }
            mDatabase.setTransactionSuccessful()
            mDatabase.endTransaction()
            mDatabase.close()
        }
    }

    fun getNewsDetailById(id: Int): NewsDetail? {
        val sql = "select " + NewsDBConfig.NewsDetail.CLUMN_ID + "," + NewsDBConfig.NewsDetail.CLUMN_TITLE + "," + NewsDBConfig.NewsDetail.CLUMN_CONTENT + "," + NewsDBConfig.NewsDetail.CLUMN_ADD_TIME + " from " + NewsDBConfig.NewsDetail.TABLE_NAME + " where " + NewsDBConfig.NewsDetail.CLUMN_ID + " = " + id
        val cursor = mDatabase.rawQuery(sql, null)
        var detail: NewsDetail? = null
        if (cursor != null && cursor.count > 0) {
            cursor.moveToNext()
            detail = NewsDetail()
            detail.id = cursor.getInt(0)
            detail.title = cursor.getString(1)
            detail.content = cursor.getString(2)
            detail.addtime = cursor.getString(3)
        }
        return detail
    }
}
