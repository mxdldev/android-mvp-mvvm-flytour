package com.fly.tour.news.contract;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BaseView;

import java.util.List;

import io.reactivex.Observable;
/**
 * Description: <NewsListContract><br>
 * Author:      mxdl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsTypeContract {
    interface Presenter{
        void getListNewsType();
    }
    interface View extends BaseView {
        void showListNewsType(List<NewsType> list);
        void initTabLayout();
    }
    interface Model{
        Observable<RespDTO<List<NewsType>>> getListNewsType();
    }
}
