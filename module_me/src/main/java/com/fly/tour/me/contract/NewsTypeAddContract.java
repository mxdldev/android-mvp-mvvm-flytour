package com.fly.tour.me.contract;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BaseView;

import io.reactivex.Observable;

/**
 * Description: <NewsTypeAddContract><br>
 * Author:      mxdl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsTypeAddContract {
    interface Presenter{
        void addNewsType(String typename);
    }
    interface View extends BaseView {

    }
    interface Model{
        Observable<RespDTO<NewsType>> addNewsType(NewsType type);
    }
}
