package com.fly.tour.news.model;

import android.content.Context;

import com.fly.tour.api.NewsTypeService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.news.contract.NewsTypeContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
/**
 * Description: <NewsTypeModel><br>
 * Author:      mxdl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeModel extends BaseModel implements NewsTypeContract.Model {
    private NewsTypeService mNewsTypeService;
    @Inject
    public NewsTypeModel(Context context) {
        super(context);
        mNewsTypeService = RetrofitManager.getInstance().getNewsTypeService();
    }

    @Override
    public Observable<RespDTO<List<NewsType>>> getListNewsType() {
      return mNewsTypeService.getListNewsType(RetrofitManager.getInstance().TOKEN)
                .compose(RxAdapter.bindUntilEvent(getLifecycle()))
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}
