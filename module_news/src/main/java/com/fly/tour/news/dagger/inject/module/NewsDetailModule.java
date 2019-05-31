package com.fly.tour.news.dagger.inject.module;

import android.content.Context;

import com.fly.tour.news.contract.NewsDetailContract;
import com.fly.tour.news.dagger.model.NewsDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Description: <NewsDetailModule><br>
 * Author:      gxl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Module
public class NewsDetailModule {
    private NewsDetailContract.View mView;
    public NewsDetailModule(NewsDetailContract.View view) {
        this.mView = view;
    }
    @Provides
    public Context providerContext(){
        return mView.getContext();
    }
    @Provides
    public NewsDetailContract.View providerNewsDetailView(){
        return mView;
    }

}
