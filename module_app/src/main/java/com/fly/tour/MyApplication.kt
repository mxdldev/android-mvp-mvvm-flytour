package com.fly.tour

import com.fly.tour.common.BaseApplication
import com.fly.tour.common.manager.NewsDBManager

/**
 * Description: <MyApplication><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        NewsDBManager.getInstance(this)?.initNewsDB()
    }
}