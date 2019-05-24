package com.fly.tour.db;

/**
 * Description: <NewsDBConfig><br>
 * Author:      gxl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsDBConfig {
    String DB_NAME = "fly_news.db";
    int VERSION_CODE = 1;


    interface NewsType {
        String TABLE_NAME = "news_type";
        String CLUMN_TYPE_NAME = "typename";
        String CREATE_TABLE = "create table " + TABLE_NAME + " (id integer PRIMARY KEY autoincrement," + CLUMN_TYPE_NAME + " varchar(64))";
    }

    interface NewsDetail {
        String TABLE_NAME = "news_detail";
        String CLUMN_TYPE_ID = "typeid";
        String CLUMN_TITLE = "title";
        String CLUMN_CONTENT = "content";
        String CREATE_TABLE = "create table " + TABLE_NAME + " (id integer PRIMARY KEY autoincrement, " + CLUMN_TYPE_ID + " integer," + CLUMN_TITLE + " varchar(64)," + CLUMN_CONTENT + " text)";

    }
}
