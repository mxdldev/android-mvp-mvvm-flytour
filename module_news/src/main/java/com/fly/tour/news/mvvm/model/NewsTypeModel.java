package com.fly.tour.news.mvvm.model;

import android.app.Application;
import com.fly.tour.api.NewsTypeService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvvm.model.BaseModel;
import java.util.List;

import io.reactivex.Observable;
/**
 * Description: <NewsDetailModel><br>
 * Author:      mxdl<br>
 * Date:        2019/5/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeModel extends BaseModel {
    private NewsTypeService mNewsTypeService;

    public NewsTypeModel(Application application) {
        super(application);
        mNewsTypeService = RetrofitManager.getInstance().getNewsTypeService();
    }

    public Observable<RespDTO<List<NewsType>>> getListNewsType() {
        return mNewsTypeService.getListNewsType()
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}
