package com.fly.tour.common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Description: <初始化应用程序><br>
 * Author:      gxl<br>
 * Date:        2018/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
    public static BaseApplication getInstance(){
        return mApplication;
    }
}
