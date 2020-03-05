package com.fly.tour.news.contract

import com.fly.tour.common.mvp.BaseRefreshView
import com.fly.tour.db.entity.NewsDetail

/**
 * Description: <NewsListContract><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
interface NewsListContract {
    interface Presenter{}
    interface View<NewsDetail>: BaseRefreshView<NewsDetail>{}
    interface Model{
        fun  getListNewsByType(type:Int):List<NewsDetail>?
    }
}