package com.fly.tour.news

import com.fly.tour.common.BaseApplication
import com.fly.tour.common.manager.NewsDBManager

/**
 * Description: <><br></br>
 * Author:      mxdl<br></br>
 * Date:        2018/12/27<br></br>
 * Version:     V1.0.0<br></br>
 * Update:     <br></br>
 */
class NewsApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        NewsDBManager.getInstance(this)!!.initNewsDB()
    }
}