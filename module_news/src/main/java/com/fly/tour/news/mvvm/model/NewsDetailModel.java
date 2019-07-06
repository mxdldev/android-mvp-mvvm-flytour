package com.fly.tour.news.mvvm.model;

import android.app.Application;
import android.content.Context;

import com.fly.tour.api.NewsDetailService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.common.mvvm.model.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Description: <NewsDetailModel><br>
 * Author:      mxdl<br>
 * Date:        2019/5/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailModel extends BaseModel {
    private NewsDetailService mNewsDetailService;

    public NewsDetailModel(Application application) {
        super(application);
        mNewsDetailService = RetrofitManager.getInstance().getNewsDetailService();
    }

    public Observable<RespDTO<NewsDetail>> getNewsDetailById(int id) {
        return mNewsDetailService.getNewsDetailById(RetrofitManager.getInstance().TOKEN,id)
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}
