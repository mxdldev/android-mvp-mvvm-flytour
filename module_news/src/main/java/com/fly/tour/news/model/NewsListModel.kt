package com.fly.tour.news.model

import android.content.Context
import com.fly.tour.common.mvp.BaseModel
import com.fly.tour.db.dao.NewsDetailDao
import com.fly.tour.db.entity.NewsDetail
import com.fly.tour.news.contract.NewsListContract

/**
 * Description: <NewsListModel><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsListModel(context:Context) : BaseModel(context),NewsListContract.Model {
    private val mDetailDao by lazy { NewsDetailDao(context)}
    override fun getListNewsByType(type: Int): List<NewsDetail>? {
        return mDetailDao.getListNewsByType(type)
    }
}