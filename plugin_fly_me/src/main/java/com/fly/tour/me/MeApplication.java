package com.fly.tour.me;

import com.fly.tour.common.BaseApplication;
import com.fly.tour.common.manager.NewsDBManager;

/**
 * Description: <MeApplication><br>
 * Author:      mxdl<br>
 * Date:        2020/3/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MeApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        NewsDBManager.getInstance(this).initNewsDB();
    }
}
