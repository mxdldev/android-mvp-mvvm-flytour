package com.fly.tour.me.mvvm.model;

import com.fly.tour.api.NewsTypeService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvvm.model.BaseModel;
import com.fly.tour.me.contract.NewsTypeAddContract;

import io.reactivex.Observable;

/**
 * Description: <NewsTypeAddModel><br>
 * Author:      gxl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddModel extends BaseModel{
    private NewsTypeService mNewsTypeService;
    public NewsTypeAddModel() {
        super();
        mNewsTypeService = RetrofitManager.getInstance().getNewsTypeService();
    }
    public Observable<RespDTO<NewsType>> addNewsType(NewsType type) {
        return mNewsTypeService.addNewsType(RetrofitManager.getInstance().TOKEN,type)
                .compose(RxAdapter.<RespDTO<NewsType>>bindUntilEvent(getLifecycleProvider()))
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }

    @Override
    public void onCleared() {

    }
}
