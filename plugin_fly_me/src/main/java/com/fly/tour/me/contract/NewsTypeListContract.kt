package com.fly.tour.me.contract

import com.fly.tour.common.mvp.BaseRefreshContract
import com.fly.tour.common.mvp.BaseRefreshView
import com.fly.tour.db.entity.NewsType

/**
 * Description: <NewsTypeListContract><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
interface NewsTypeListContract {
    interface Presenter : BaseRefreshContract.Presenter {
        fun deleteNewsTypeById(id: Int)
    }

    interface View<NewsType> : BaseRefreshView<NewsType> {

    }

    interface Model {
        fun getListNewsType(): List<NewsType>?
        fun deleteNewsTypeById(id: Int): Boolean
    }
}