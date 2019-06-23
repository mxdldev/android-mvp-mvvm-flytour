package com.fly.tour.news.contract;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.common.mvp.BaseRefreshView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description: <NewsListContract><br>
 * Author:      gxl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsListContract {
    interface Presenter{
    }
    interface View<NewsDetail> extends BaseRefreshView<NewsDetail> {}
    interface Model{
        Observable<RespDTO<List<NewsDetail>>> getListNewsByType(int type);
    }
}
