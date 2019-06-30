package com.fly.tour;

import com.fly.tour.api.RetrofitManager;
import com.fly.tour.common.BaseApplication;

/**
 * Description: <MyApplication><br>
 * Author:      gxl<br>
 * Date:        2018/12/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //NewsDBManager.getInstance(this).initNewsDB();
        RetrofitManager.init(this);
    }
}
