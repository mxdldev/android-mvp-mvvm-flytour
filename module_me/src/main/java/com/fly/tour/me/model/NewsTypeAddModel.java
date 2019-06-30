package com.fly.tour.me.model;

import android.content.Context;

import com.fly.tour.api.NewsTypeService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.me.contract.NewsTypeAddContract;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Description: <NewsTypeAddModel><br>
 * Author:      gxl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddModel extends BaseModel implements NewsTypeAddContract.Model {
    private NewsTypeService mNewsTypeService;
    @Inject
    public NewsTypeAddModel(Context context) {
        super(context);
        mNewsTypeService = RetrofitManager.getInstance().getNewsTypeService();
    }


    @Override
    public Observable<RespDTO<NewsType>> addNewsType(NewsType type) {
        return mNewsTypeService.addNewsType(RetrofitManager.getInstance().TOKEN,type)
                .compose(RxAdapter.<RespDTO<NewsType>>bindUntilEvent(getLifecycle()))
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}
