package com.fly.tour.news.contract;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.common.mvp.BaseView;

import io.reactivex.Observable;

/**
 * Description: <NewsDetailContract><br>
 * Author:      gxl<br>
 * Date:        2019/5/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsDetailContract {
    interface Presenter{
        void getNewsDetailById(int id);
    }
    interface View extends BaseView {
        void showNewsDetail(NewsDetail newsDetail);
    }
    interface Model{
        Observable<RespDTO<NewsDetail>> getNewsDetailById(int id);
    }
}
