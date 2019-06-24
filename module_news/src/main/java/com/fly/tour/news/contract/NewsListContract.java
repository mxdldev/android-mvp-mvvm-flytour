package com.fly.tour.news.contract;

import com.fly.tour.common.mvp.BaseRefreshView;
import com.fly.tour.common.mvp.BaseView;
import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.db.entity.NewsType;

import java.util.List;

/**
 * Description: <NewsListContract><br>
 * Author:      mxdl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsListContract {
    interface Presenter{
    }
    interface View<NewsDetail> extends BaseRefreshView<NewsDetail> {}
    interface Model{
        List<NewsDetail> getNewsType(int type);
    }
}
