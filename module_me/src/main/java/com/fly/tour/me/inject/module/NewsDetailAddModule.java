package com.fly.tour.me.inject.module;

import android.content.Context;

import com.fly.tour.me.contract.NewsDetailAddContract;
import com.fly.tour.me.contract.NewsTypeAddContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description: <NewsTypeListModule><br>
 * Author:      mxdl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Module
public class NewsDetailAddModule {
    private NewsDetailAddContract.View mView;
    public NewsDetailAddModule(NewsDetailAddContract.View view) {
        this.mView = view;
    }
    @Provides
    public Context providerContext(){
        return mView.getContext();
    }
    @Provides
    public NewsDetailAddContract.View providerNewsDetailView(){
        return mView;
    }
}
