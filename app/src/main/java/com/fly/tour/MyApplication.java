package com.fly.tour;

import android.content.Context;

import com.didi.virtualapk.PluginManager;
import com.fly.tour.common.BaseApplication;
import com.fly.tour.common.manager.NewsDBManager;

/**
 * Description: <MyApplication><br>
 * Author:      mxdl<br>
 * Date:        2018/12/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyApplication extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
