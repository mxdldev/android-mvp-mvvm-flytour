package com.fly.tour.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Description: <NewsDBHelper><br>
 * Author:      mxdl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsDBHelper private constructor(context: Context) : SQLiteOpenHelper(context, NewsDBConfig.DB_NAME, null, NewsDBConfig.VERSION_CODE) {

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(NewsDBConfig.NewsType.CREATE_TABLE)
        db.execSQL(NewsDBConfig.NewsDetail.CREATE_TABLE)
    }

    companion object {
        private var mNewsDBHelper: NewsDBHelper? = null
        fun getInstance(context: Context): NewsDBHelper? {
            if (mNewsDBHelper == null) {
                synchronized(NewsDBHelper::class.java) {
                    if (mNewsDBHelper == null) {
                        mNewsDBHelper = NewsDBHelper(context)
                    }
                }
            }
            return mNewsDBHelper
        }
    }
}
