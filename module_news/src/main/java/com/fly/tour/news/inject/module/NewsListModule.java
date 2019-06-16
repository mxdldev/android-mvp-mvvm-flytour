package com.fly.tour.news.inject.module;

import android.content.Context;

import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.news.contract.NewsDetailContract;
import com.fly.tour.news.contract.NewsListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description: <NewsListModule><br>
 * Author:      gxl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Module
public class NewsListModule {
    private NewsListContract.View<NewsDetail> mView;
    public NewsListModule(NewsListContract.View<NewsDetail> view) {
        this.mView = view;
    }
    @Provides
    public Context providerContext(){
        return mView.getContext();
    }
    @Provides
    public NewsListContract.View<NewsDetail> providerNewsDetailView(){
        return mView;
    }
}
