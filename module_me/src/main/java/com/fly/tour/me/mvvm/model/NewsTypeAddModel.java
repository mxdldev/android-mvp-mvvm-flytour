package com.fly.tour.me.mvvm.model;

import android.app.Application;

import com.fly.tour.api.NewsTypeService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.news.NewsType;
import com.fly.tour.common.mvvm.model.BaseModel;

import io.reactivex.Observable;

/**
 * Description: <NewsTypeAddModel><br>
 * Author:      mxdl<br>
 * Date:        2019/07/02<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddModel extends BaseModel{
    private NewsTypeService mNewsTypeService;
    public NewsTypeAddModel(Application application) {
        super(application);
        mNewsTypeService = RetrofitManager.getInstance().getNewsTypeService();
    }
    public Observable<RespDTO<NewsType>> addNewsType(NewsType type) {
        return mNewsTypeService.addNewsType(RetrofitManager.getInstance().TOKEN,type)
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }

}
