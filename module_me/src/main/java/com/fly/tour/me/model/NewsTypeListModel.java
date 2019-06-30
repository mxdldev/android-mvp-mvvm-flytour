package com.fly.tour.me.model;

import android.content.Context;

import com.fly.tour.api.NewsTypeService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.me.contract.NewsTypeListContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Description: <NewsTypeListModel><br>
 * Author:      gxl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeListModel extends BaseModel implements NewsTypeListContract.Model {
    private NewsTypeService mNewsTypeService;
    @Inject
    public NewsTypeListModel(Context context) {
        super(context);
        mNewsTypeService = RetrofitManager.getInstance().getNewsTypeService();
    }


    @Override
    public Observable<RespDTO<List<NewsType>>> getListNewsType() {
        return mNewsTypeService.getListNewsType(RetrofitManager.getInstance().TOKEN)
                .compose(RxAdapter.<RespDTO<List<NewsType>>>bindUntilEvent(getLifecycle()))
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }

    @Override
    public Observable<RespDTO> deleteNewsTypeById(int id) {
        return mNewsTypeService.deleteNewsTypeById(RetrofitManager.getInstance().TOKEN,id)
                .compose(RxAdapter.<RespDTO>bindUntilEvent(getLifecycle()))
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}
