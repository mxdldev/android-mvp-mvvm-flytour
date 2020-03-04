package com.fly.tour.news;

import com.fly.tour.common.BaseApplication;
import com.fly.tour.common.manager.NewsDBManager;

/**
 * Description: <><br>
 * Author:      mxdl<br>
 * Date:        2018/12/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        NewsDBManager.getInstance(this).initNewsDB();
    }
}
