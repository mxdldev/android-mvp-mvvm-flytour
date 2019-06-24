package com.fly.tour.me.inject.module;

import android.content.Context;

import com.fly.tour.me.contract.NewsTypeAddContract;
import com.fly.tour.me.contract.NewsTypeListContract;

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
public class NewsTypeAddModule {
    private NewsTypeAddContract.View mView;
    public NewsTypeAddModule(NewsTypeAddContract.View view) {
        this.mView = view;
    }
    @Provides
    public Context providerContext(){
        return mView.getContext();
    }
    @Provides
    public NewsTypeAddContract.View providerNewsTypeAddView(){
        return mView;
    }
}
