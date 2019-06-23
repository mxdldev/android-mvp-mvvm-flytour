package com.fly.tour.me.contract;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BaseRefreshContract;
import com.fly.tour.common.mvp.BaseRefreshView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description: <NewsTypeListContract><br>
 * Author:      gxl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsTypeListContract {
    interface Presenter extends BaseRefreshContract.Presenter{
        void deleteNewsTypeById(int id);
    }
    interface View<NewsType> extends BaseRefreshView<NewsType> {

    }
    interface Model{
        Observable<RespDTO<List<NewsType>>> getListNewsType();
        Observable<RespDTO> deleteNewsTypeById(int id);
    }
}
