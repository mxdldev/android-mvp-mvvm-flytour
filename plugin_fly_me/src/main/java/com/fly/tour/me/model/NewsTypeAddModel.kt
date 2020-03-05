package com.fly.tour.me.model

import android.content.Context
import com.fly.tour.common.mvp.BaseModel
import com.fly.tour.db.dao.NewsTypeDao
import com.fly.tour.me.contract.NewsTypeAddContract

/**
 * Description: <NewsTypeAddModel><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsTypeAddModel(context: Context) : BaseModel(context), NewsTypeAddContract.Model {
    private val mNewsTypeDao: NewsTypeDao by lazy { NewsTypeDao(context) }
    override fun addNewsType(type: String): Boolean {
        return mNewsTypeDao.addNewsType(type)
    }
}