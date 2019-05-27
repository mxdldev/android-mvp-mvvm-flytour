package com.fly.tour.me.contract;

import com.fly.tour.common.mvp.BaseRefreshContract;
import com.fly.tour.common.mvp.BaseRefreshView;
import com.fly.tour.db.entity.NewsType;

import java.util.List;

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
        List<NewsType> getListNewsType();
        boolean deleteNewsTypeById(int id);
    }
}
