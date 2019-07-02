package com.fly.tour.test.presenter;

import android.util.Log;

import javax.inject.Inject;

/**
 * Description: <MainPresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainPresenter {
    @Inject
    public MainPresenter() {
    }

    public void prinitHelloWorld(){
        Log.v("MYTAG","hello world");
    }
}
