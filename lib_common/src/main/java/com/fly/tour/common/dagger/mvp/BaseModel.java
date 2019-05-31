package com.fly.tour.common.dagger.mvp;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;

import javax.inject.Inject;

/**
 * Description: <BaseModel><br>
 * Author:      gxl<br>
 * Date:        2018/3/18<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class BaseModel {
    private Context mContext;
    public BaseModel(Context context) {
        mContext = context;
    }

    private LifecycleProvider lifecycle;

    public void injectLifecycle(LifecycleProvider lifecycle) {
        this.lifecycle = lifecycle;
    }

    public LifecycleProvider getLifecycle() {
        return lifecycle;
    }

    public Context getContext() {
        return mContext;
    }

    public void destory(){}
}
