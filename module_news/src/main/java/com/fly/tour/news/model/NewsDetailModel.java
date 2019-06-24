package com.fly.tour.news.model;

import android.content.Context;

import com.fly.tour.api.NewsDetailService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.news.contract.NewsDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Description: <NewsDetailModel><br>
 * Author:      mxdl<br>
 * Date:        2019/5/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailModel extends BaseModel implements NewsDetailContract.Model {
    private NewsDetailService mNewsDetailService;
    @Inject
    public NewsDetailModel(Context context) {
        super(context);
        mNewsDetailService = RetrofitManager.getInstance().getNewsDetailService();
    }

    @Override
    public Observable<RespDTO<NewsDetail>> getNewsDetailById(int id) {
        return mNewsDetailService.getNewsDetailById(RetrofitManager.getInstance().TOKEN,id)
                .compose(RxAdapter.bindUntilEvent(getLifecycle()))
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}
