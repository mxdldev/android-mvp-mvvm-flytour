package com.fly.tour.me.contract;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description: <NewsDetailAddContract><br>
 * Author:      gxl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsDetailAddContract {
    interface Presenter{
        void addNewsDetail(int type,String title,String content);
        void getListNewsType();
    }
    interface View extends BaseView {
        void showNewsType(List<NewsType> typeList);
    }
    interface Model{
        Observable<RespDTO<NewsDetail>> addNewsDetail(int type, String title, String content);
        Observable<RespDTO<List<NewsType>>> getNewsType();
    }
}
