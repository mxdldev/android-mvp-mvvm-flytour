package com.fly.tour.news.model

import android.content.Context
import com.fly.tour.common.mvp.BaseModel
import com.fly.tour.db.dao.NewsDetailDao
import com.fly.tour.db.entity.NewsDetail
import com.fly.tour.news.contract.NewsDetailContract

/**
 * Description: <NewsDetailModel><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsDetailModel(context: Context) : BaseModel(context), NewsDetailContract.Model {
    private val mNewsDetailDao: NewsDetailDao by lazy { NewsDetailDao(context) }
    override fun getNewsDetailById(id: Int): NewsDetail? {
        return mNewsDetailDao.getNewsDetailById(id)
    }
}