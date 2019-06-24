package com.fly.tour.news.model;

import android.content.Context;

import com.fly.tour.api.NewsDetailService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.news.contract.NewsListContract;
import java.util.List;
import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Description: <NewsListModel><br>
 * Author:      mxdl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsListModel extends BaseModel implements NewsListContract.Model {
    private NewsDetailService mNewsDetailService;
    @Inject
    public NewsListModel(Context context) {
        super(context);
        mNewsDetailService = RetrofitManager.getInstance().getNewsDetailService();
    }

    @Override
    public Observable<RespDTO<List<NewsDetail>>> getListNewsByType(int typeid) {
        return mNewsDetailService.getListNewsDetailByType(RetrofitManager.getInstance().TOKEN,typeid)
        .compose(RxAdapter.<RespDTO<List<NewsDetail>>>bindUntilEvent(getLifecycle()))
        .compose(RxAdapter.exceptionTransformer())
        .compose(RxAdapter.schedulersTransformer());
    }
}
