package com.fly.tour.me

import com.fly.tour.common.BaseApplication
import com.fly.tour.common.manager.NewsDBManager

/**
 * Description: <MeApplication><br></br>
 * Author:      mxdl<br></br>
 * Date:        2020/3/4<br></br>
 * Version:     V1.0.0<br></br>
 * Update:     <br></br>
</MeApplication> */
class MeApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        NewsDBManager.getInstance(this)!!.initNewsDB()
    }
}