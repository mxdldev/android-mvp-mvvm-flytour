package com.fly.tour.news.contract;

import com.fly.tour.common.mvp.BaseView;
import com.fly.tour.db.entity.NewsDetail;

/**
 * Description: <NewsDetailContract><br>
 * Author:      mxdl<br>
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
        NewsDetail getNewsDetailById(int id);
    }
}
