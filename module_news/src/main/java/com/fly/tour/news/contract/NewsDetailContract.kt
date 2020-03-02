package com.fly.tour.news.contract

import com.fly.tour.common.mvp.BaseView
import com.fly.tour.db.entity.NewsDetail

/**
 * Description: <NewsDetailContract><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
/**
 * Description: <NewsDetailContract><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
interface NewsDetailContract {
    interface Presenter {
        fun getNewsDetailById(id: Int)
    }

    interface View : BaseView {
        fun showViewDetail(newsDetail: NewsDetail?)
    }

    interface Model {
        fun getNewsDetailById(id: Int): NewsDetail?
    }
}