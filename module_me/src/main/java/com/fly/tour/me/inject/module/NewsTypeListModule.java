package com.fly.tour.me.inject.module;

import android.content.Context;

import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.me.contract.NewsTypeListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description: <NewsTypeListModule><br>
 * Author:      gxl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Module
public class NewsTypeListModule {
    private NewsTypeListContract.View<NewsType> mView;
    public NewsTypeListModule(NewsTypeListContract.View<NewsType> view) {
        this.mView = view;
    }
    @Provides
    public Context providerContext(){
        return mView.getContext();
    }
    @Provides
    public NewsTypeListContract.View<NewsType> providerNewsTypeListView(){
        return mView;
    }
}
