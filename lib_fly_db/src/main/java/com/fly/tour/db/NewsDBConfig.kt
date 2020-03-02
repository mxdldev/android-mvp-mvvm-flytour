package com.fly.tour.db

/**
 * Description: <NewsDBConfig><br>
 * Author:      mxdl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
interface NewsDBConfig {

    interface NewsType {
        companion object {
            val TABLE_NAME = "news_type"
            val CLUMN_ID = "id"
            val CLUMN_TYPE_NAME = "typename"
            val CLUMN_ADD_TIME = "addtime"
            val CREATE_TABLE = "create table $TABLE_NAME ($CLUMN_ID integer PRIMARY KEY autoincrement,$CLUMN_TYPE_NAME varchar(64),$CLUMN_ADD_TIME datetime default (datetime('now','localtime')))"
        }
    }

    interface NewsDetail {
        companion object {
            val TABLE_NAME = "news_detail"
            val CLUMN_ID = "id"
            val CLUMN_TYPE_ID = "typeid"
            val CLUMN_TITLE = "title"
            val CLUMN_CONTENT = "content"
            val CLUMN_ADD_TIME = "addtime"
            val CREATE_TABLE = "create table $TABLE_NAME ($CLUMN_ID integer PRIMARY KEY autoincrement, $CLUMN_TYPE_ID integer,$CLUMN_TITLE varchar(64),$CLUMN_CONTENT text,$CLUMN_ADD_TIME datetime default (datetime('now','localtime')))"
        }
    }

    companion object {
        val DB_NAME = "fly_news.db"
        val VERSION_CODE = 1
    }
}
