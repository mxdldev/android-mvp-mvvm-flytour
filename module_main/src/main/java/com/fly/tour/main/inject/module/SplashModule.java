package com.fly.tour.main.inject.module;

import android.content.Context;

import com.fly.tour.main.contract.SplashContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description: <NewsDetailModule><br>
 * Author:      mxdl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Module
public class SplashModule {
    private SplashContract.View mView;
    public SplashModule(SplashContract.View view) {
        this.mView = view;
    }
    @Provides
    public Context providerContext(){
        return mView.getContext();
    }
    @Provides
    public SplashContract.View providerNewsDetailView(){
        return mView;
    }

}
