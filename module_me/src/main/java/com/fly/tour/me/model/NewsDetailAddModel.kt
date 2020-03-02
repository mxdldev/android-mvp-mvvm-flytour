package com.fly.tour.me.model

import android.content.Context
import com.fly.tour.common.mvp.BaseModel
import com.fly.tour.db.dao.NewsDetailDao
import com.fly.tour.me.contract.NewsDetailAddContract

/**
 * Description: <NewsDetailAddModel><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsDetailAddModel(content: Context) : BaseModel(content), NewsDetailAddContract.Model {
    private val mNewsDetailDao: NewsDetailDao by lazy { NewsDetailDao(context) }
    override fun addNewsDetail(type: Int, title: String, content: String): Boolean {
        return mNewsDetailDao.addNewsDetail(type, title, content)
    }

}