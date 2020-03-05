package com.fly.tour.db.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import com.fly.tour.db.NewsDBConfig
import com.fly.tour.db.NewsDBHelper
import com.fly.tour.db.entity.NewsType

import java.util.ArrayList

/**
 * Description: <NewsTypeDao><br>
 * Author:      mxdl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsTypeDao(context: Context) {
    private val mDatabase: SQLiteDatabase

    init {
        mDatabase = NewsDBHelper.getInstance(context)!!.readableDatabase
    }

    fun getListNewsType(): List<NewsType>? {
        val query = "select * from " + NewsDBConfig.NewsType.TABLE_NAME
        val cursor = mDatabase.rawQuery(query, null)
        var typeList: MutableList<NewsType>? = null
        if (cursor != null && cursor.count > 0) {
            typeList = ArrayList()
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val typename = cursor.getString(1)
                val addtime = cursor.getString(2)
                val newsType = NewsType()
                newsType.id = id
                newsType.typename = typename
                newsType.addtime = addtime
                typeList.add(newsType)
            }
        }
        return typeList
    }

    fun isEmpty(): Boolean {
        val sql = "select * from " + NewsDBConfig.NewsType.TABLE_NAME
        val cursor = mDatabase.rawQuery(sql, null)
        return if (cursor != null && cursor.count > 0) {
            false
        } else {
            true
        }
    }

    fun addNewsType(typename: String): Boolean {
        val values = ContentValues()
        values.put(NewsDBConfig.NewsType.CLUMN_TYPE_NAME, typename)
        return mDatabase.insert(NewsDBConfig.NewsType.TABLE_NAME, null, values) > 0
    }

    fun addNewsType(id: Int, typename: String?): Boolean {
        val values = ContentValues()
        values.put(NewsDBConfig.NewsType.CLUMN_ID, id)
        values.put(NewsDBConfig.NewsType.CLUMN_TYPE_NAME, typename)
        return mDatabase.insert(NewsDBConfig.NewsType.TABLE_NAME, null, values) > 0
    }

    fun deleteNewsType(id: Int): Boolean {
        return mDatabase.delete(
            NewsDBConfig.NewsType.TABLE_NAME,
            "id = ?",
            arrayOf(id.toString() + "")
        ) > 0
    }

    fun addListNewStype(newsTypeList: List<NewsType>) {
        mDatabase.beginTransaction()
        for (type in newsTypeList) {
            addNewsType(type.id, type.typename)
        }
        mDatabase.setTransactionSuccessful()
        mDatabase.endTransaction()
        mDatabase.close()
    }
}
